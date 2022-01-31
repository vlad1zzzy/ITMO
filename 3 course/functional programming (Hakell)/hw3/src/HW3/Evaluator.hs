{-# LANGUAGE MonoLocalBinds #-}
{-# LANGUAGE TupleSections #-}

module HW3.Evaluator (eval) where

import Codec.Compression.Zlib (bestCompression, compressLevel, compressWith, decompress, defaultCompressParams)
import Codec.Serialise (deserialise, serialise)
import Control.Arrow (first)
import Control.Monad (foldM)
import Control.Monad.Trans.Except (ExceptT (..), runExceptT, throwE)
import qualified Data.ByteString.Char8 as C
import qualified Data.ByteString.Lazy as L
import Data.Char (chr, ord)
import Data.Foldable (toList)
import qualified Data.Map as M
import Data.Maybe (fromMaybe)
import Data.Ratio (denominator, numerator)
import Data.Semigroup (stimes)
import qualified Data.Sequence as Seq
import qualified Data.Text as T
import Data.Text.Encoding (decodeUtf8', encodeUtf8)
import Data.Time.Clock (addUTCTime, diffUTCTime)
import HW3.Base
import Text.Read (readMaybe)

-- | Main evaluator function
eval :: HiMonad m => HiExpr -> m (Either HiError HiValue)
eval = runExceptT . eval'

-- | Evaluator function for HiExpr:
-- |    HiExprValue -> value
-- |    HiExprApply ->
-- |        HiValueFunction -> evaluate function
-- |        HiValueString / HiValueList / HiValueBytes -> evaluate string like methods
-- |        HiValueDict -> evaluate dictionary queries
-- |        otherwise -> HiErrorInvalidFunction
-- |    HiValueDict -> evaluate dictionary
-- |    HiExprRun ->
-- |        HiValueAction -> evaluate action
-- |        otherwise -> HiErrorInvalidFunction
eval' :: HiMonad m => HiExpr -> ExceptT HiError m HiValue
eval' (HiExprValue v) = return v
eval' (HiExprApply f args) = do
  f' <- eval' f
  case f' of
    HiValueFunction func -> evalFunction func args
    HiValueString s -> evalStringSeqMethod (T.length) (getTextIndex s) (getTextSlice s) s args
    HiValueList s -> evalStringSeqMethod (Seq.length) (getSeqIndex s) (getSeqSlice s) s args
    HiValueBytes b -> evalStringSeqMethod (C.length) (getBytesIndex b) (getBytesSlice b) b args
    HiValueDict b -> evalGetDictValue b args
    _ -> throwE HiErrorInvalidFunction
eval' (HiExprDict d) =
  mapM (\(l, r) -> eval' l >>= \l' -> eval' r >>= \r' -> return (l', r')) d >>= return . HiValueDict . M.fromList
eval' (HiExprRun r) = do
  act <- eval' r
  case act of
    HiValueAction a -> ExceptT $ fmap Right $ runAction a
    _ -> throwE HiErrorInvalidArgument

-- | Evaluator for HiFun:
evalFunction :: HiMonad m => HiFun -> [HiExpr] -> ExceptT HiError m HiValue
evalFunction f = case f of
  HiFunAdd -> evalBinary evalAddConcat
  HiFunSub -> evalBinary evalSub
  HiFunMul -> evalBinary evalMulRepeat
  HiFunDiv -> evalBinary evalDivConcat
  HiFunAnd -> evalBinaryLazy evalAnd
  HiFunOr -> evalBinaryLazy evalOr
  HiFunLessThan -> evalBinary (evalCompare (<))
  HiFunGreaterThan -> evalBinary (evalCompare (>))
  HiFunEquals -> evalBinary (evalCompare (==))
  HiFunNotLessThan -> evalBinary (evalCompare (>=))
  HiFunNotGreaterThan -> evalBinary (evalCompare (<=))
  HiFunNotEquals -> evalBinary (evalCompare (/=))
  HiFunNot -> evalUnary evalNot
  HiFunIf -> evalIf
  HiFunLength -> evalUnary evalLength
  HiFunToUpper -> evalUnary evalUpper
  HiFunToLower -> evalUnary evalLower
  HiFunReverse -> evalUnary evalReverse
  HiFunTrim -> evalUnary evalTrim
  HiFunList -> evalList
  HiFunRange -> evalBinary evalRange
  HiFunFold -> evalBinary evalFold
  HiFunPackBytes -> evalUnary evalPack
  HiFunUnpackBytes -> evalUnary evalUnpack
  HiFunEncodeUtf8 -> evalUnary evalEncode
  HiFunDecodeUtf8 -> evalUnary evalDecode
  HiFunZip -> evalUnary evalZip
  HiFunUnzip -> evalUnary evalUnzip
  HiFunSerialise -> evalUnary evalSerialise
  HiFunDeserialise -> evalUnary evalDeserialise
  HiFunRead -> evalUnary evalRead
  HiFunWrite -> evalBinary evalWrite
  HiFunMkDir -> evalUnary evalMkDir
  HiFunChDir -> evalUnary evalChDir
  HiFunParseTime -> evalUnary evalTime
  HiFunRand -> evalBinary evalRand
  HiFunEcho -> evalUnary evalEcho
  HiFunCount -> evalUnary evalCount
  HiFunKeys -> evalUnary evalKeys
  HiFunValues -> evalUnary evalValues
  HiFunInvert -> evalUnary evalInvert

-- | Evaluator for HiFunAdd. Summary for numbers, concatenation for `string like` types, time adding
evalAddConcat :: HiMonad m => HiValue -> HiValue -> ExceptT HiError m HiValue
evalAddConcat (HiValueNumber a) (HiValueNumber b) = return $ HiValueNumber $ a + b
evalAddConcat (HiValueString a) (HiValueString b) = return $ HiValueString $ T.concat $ [a, b]
evalAddConcat (HiValueList a) (HiValueList b) = return $ HiValueList $ a Seq.>< b
evalAddConcat (HiValueBytes a) (HiValueBytes b) = return $ HiValueBytes $ C.append a b
evalAddConcat (HiValueTime a) (HiValueNumber b) = return $ HiValueTime $ addUTCTime (fromRational b) a
evalAddConcat (HiValueNumber a) (HiValueTime b) = return $ HiValueTime $ addUTCTime (fromRational a) b
evalAddConcat _ _ = throwE HiErrorInvalidArgument

-- | Evaluator for HiFunSub. Subtract for numbers, time diff
evalSub :: HiMonad m => HiValue -> HiValue -> ExceptT HiError m HiValue
evalSub (HiValueNumber a) (HiValueNumber b) = return $ HiValueNumber $ a - b
evalSub (HiValueTime a) (HiValueTime b) = return $ HiValueNumber $ toRational $ diffUTCTime a b
evalSub _ _ = throwE HiErrorInvalidArgument

-- | Evaluator for HiFunMul. Multiply for numbers, repetition for `string like` types
evalMulRepeat :: HiMonad m => HiValue -> HiValue -> ExceptT HiError m HiValue
evalMulRepeat (HiValueNumber a) (HiValueNumber b) = return $ HiValueNumber $ a * b
evalMulRepeat (HiValueString a) (HiValueNumber b) = evalRepeat a b HiValueString
evalMulRepeat (HiValueList a) (HiValueNumber b) = evalRepeat a b HiValueList
evalMulRepeat (HiValueBytes a) (HiValueNumber b) = evalRepeat a b HiValueBytes
evalMulRepeat _ _ = throwE HiErrorInvalidArgument

-- | Evaluator for HiFunDiv. Division for numbers, concatenation for `string like` types
evalDivConcat :: HiMonad m => HiValue -> HiValue -> ExceptT HiError m HiValue
evalDivConcat (HiValueNumber _) (HiValueNumber 0) = throwE HiErrorDivideByZero
evalDivConcat (HiValueNumber a) (HiValueNumber b) = return $ HiValueNumber $ a / b
evalDivConcat (HiValueString a) (HiValueString b) = return $ HiValueString $ T.concat [a, T.pack "/", b]
evalDivConcat _ _ = throwE HiErrorInvalidArgument

-- | Evaluator for HiFunAdd. Boolean add
evalAnd :: HiMonad m => [HiExpr] -> ExceptT HiError m HiValue
evalAnd args = (eval' $ head args) >>= evalAndOr return (const $ eval' $ last args)

-- | Evaluator for HiFunOr. Boolean or
evalOr :: HiMonad m => [HiExpr] -> ExceptT HiError m HiValue
evalOr args = (eval' $ head args) >>= evalAndOr (const $ eval' $ last args) return

-- | Evaluator for `comparing like` fun types
evalCompare :: HiMonad m => (HiValue -> HiValue -> Bool) -> HiValue -> HiValue -> ExceptT HiError m HiValue
evalCompare f a b = return $ HiValueBool $ f a b

-- | Evaluator for HiFunNot. Boolean not
evalNot :: HiMonad m => HiValue -> ExceptT HiError m HiValue
evalNot (HiValueBool a) = return $ HiValueBool $ not a
evalNot _ = throwE HiErrorInvalidArgument

-- | Evaluator for HiFunIf. Depends on first predicate returns second or third argument
evalIf :: HiMonad m => [HiExpr] -> ExceptT HiError m HiValue
evalIf l
  | length l == 3 = (eval' $ head l) >>= \a -> if a == HiValueBool True then eval' $ l !! 1 else eval' $ last l
  | otherwise = throwE HiErrorArityMismatch

-- | Evaluator for HiFunLength. Returns length of `string like` types
evalLength :: HiMonad m => HiValue -> ExceptT HiError m HiValue
evalLength (HiValueString s) = return $ HiValueNumber $ toRational $ T.length s
evalLength (HiValueList l) = return $ HiValueNumber $ toRational $ length l
evalLength (HiValueBytes b) = return $ HiValueNumber $ toRational $ C.length b
evalLength _ = throwE HiErrorInvalidArgument

-- | Evaluator for HiFunUpper. Returns string in uppercase
evalUpper :: HiMonad m => HiValue -> ExceptT HiError m HiValue
evalUpper (HiValueString s) = return $ HiValueString $ T.toUpper s
evalUpper _ = throwE HiErrorInvalidArgument

-- | Evaluator for HiFunLower. Returns string in lowercase
evalLower :: HiMonad m => HiValue -> ExceptT HiError m HiValue
evalLower (HiValueString s) = return $ HiValueString $ T.toLower s
evalLower _ = throwE HiErrorInvalidArgument

-- | Evaluator for HiFunReverse. Returns reversed input for `string like` types
evalReverse :: HiMonad m => HiValue -> ExceptT HiError m HiValue
evalReverse (HiValueString s) = return $ HiValueString $ T.reverse s
evalReverse (HiValueList l) = return $ HiValueList $ Seq.reverse l
evalReverse (HiValueBytes b) = return $ HiValueBytes $ C.reverse b
evalReverse _ = throwE HiErrorInvalidArgument

-- | Evaluator for HiFunTrim. Returns string without starting and ending spaces
evalTrim :: HiMonad m => HiValue -> ExceptT HiError m HiValue
evalTrim (HiValueString s) = return $ HiValueString $ T.strip s
evalTrim _ = throwE HiErrorInvalidArgument

-- | Evaluator for HiFunList. Construct and return list
evalList :: HiMonad m => [HiExpr] -> ExceptT HiError m HiValue
evalList l = mapM eval' l >>= return . HiValueList . Seq.fromList

-- | Evaluator for HiFunRange. Returns list of numbers in given range
evalRange :: HiMonad m => HiValue -> HiValue -> ExceptT HiError m HiValue
evalRange (HiValueNumber a) (HiValueNumber b) = return $ HiValueList $ Seq.fromList $ map (HiValueNumber . toRational) [a .. b]
evalRange _ _ = throwE HiErrorInvalidArgument

-- | Evaluator for HiFunFold
evalFold :: HiMonad m => HiValue -> HiValue -> ExceptT HiError m HiValue
evalFold _ (HiValueList b) | Seq.length b == 1 = return $ Seq.index b 0
evalFold (HiValueFunction a) (HiValueList b)
  | Seq.length b == 0 = return HiValueNull
  | otherwise = foldM (foldFunction a) (Seq.index b 0) (Seq.drop 1 b)
  where
    foldFunction :: HiMonad m => HiFun -> HiValue -> HiValue -> ExceptT HiError m HiValue
    foldFunction f acc el = eval' $ (HiExprApply $ HiExprValue $ HiValueFunction $ f) [HiExprValue acc, HiExprValue el]
evalFold _ _ = throwE HiErrorInvalidArgument

-- | Evaluator for HiFunPack. Converts list of numbers (0..255) into bytestring
evalPack :: HiMonad m => HiValue -> ExceptT HiError m HiValue
evalPack (HiValueList l) = foldM packValue C.empty l >>= return . HiValueBytes
  where
    packValue :: HiMonad m => C.ByteString -> HiValue -> ExceptT HiError m C.ByteString
    packValue acc (HiValueNumber el) = rationalToHexChar el >>= return . C.append acc . C.singleton
    packValue _ _ = throwE HiErrorInvalidArgument
evalPack _ = throwE HiErrorInvalidArgument

-- | Evaluator for HiFunUnpack. Converts bytestring into list of numbers (0..255)
evalUnpack :: HiMonad m => HiValue -> ExceptT HiError m HiValue
evalUnpack (HiValueBytes b) = evalList $ map (HiExprValue . HiValueNumber . toRational . ord) $ C.unpack b
evalUnpack _ = throwE HiErrorInvalidArgument

-- | Evaluator for HiFunEncode. Returns encoded string into bytestring
evalEncode :: HiMonad m => HiValue -> ExceptT HiError m HiValue
evalEncode (HiValueString s) = (evalUnpack $ HiValueBytes $ encodeUtf8 s) >>= evalPack
evalEncode _ = throwE HiErrorInvalidArgument

-- | Evaluator for HiFunDecode. If the input contains any invalid UTF-8 data, returns null, otherwise the decoded string
evalDecode :: HiMonad m => HiValue -> ExceptT HiError m HiValue
evalDecode (HiValueBytes b) = return $ do
  case decodeUtf8' b of
    Left _ -> HiValueNull
    Right r -> HiValueString r
evalDecode _ = throwE HiErrorInvalidArgument

-- | Evaluator for HiFunZip. Compress bytestring
evalZip :: HiMonad m => HiValue -> ExceptT HiError m HiValue
evalZip (HiValueBytes b) =
  return $
    HiValueBytes $
      L.toStrict $
        compressWith defaultCompressParams {compressLevel = bestCompression} $ L.fromStrict b
evalZip _ = throwE HiErrorInvalidArgument

-- | Evaluator for HiFunUnzip. Decompress bytestring
evalUnzip :: HiMonad m => HiValue -> ExceptT HiError m HiValue
evalUnzip (HiValueBytes b) = return $ HiValueBytes $ L.toStrict $ decompress $ L.fromStrict b
evalUnzip _ = throwE HiErrorInvalidArgument

-- | Evaluator for HiFunSerialise. Serialise value
evalSerialise :: HiMonad m => HiValue -> ExceptT HiError m HiValue
evalSerialise a = return $ HiValueBytes $ L.toStrict $ serialise a

-- | Evaluator for HiFunDeserialise. Deserialise value
evalDeserialise :: HiMonad m => HiValue -> ExceptT HiError m HiValue
evalDeserialise (HiValueBytes b) = return $ deserialise $ L.fromStrict b
evalDeserialise _ = throwE HiErrorInvalidArgument

-- | Evaluator for HiFunRead. Returns HiActionRead
evalRead :: HiMonad m => HiValue -> ExceptT HiError m HiValue
evalRead (HiValueString s) = return $ HiValueAction $ HiActionRead $ T.unpack s
evalRead _ = throwE HiErrorInvalidArgument

-- | Evaluator for HiFunWrite. Returns HiActionWrite
evalWrite :: HiMonad m => HiValue -> HiValue -> ExceptT HiError m HiValue
evalWrite (HiValueString l) (HiValueString r) = return $ HiValueAction $ HiActionWrite (T.unpack l) $ C.pack $ T.unpack r
evalWrite _ _ = throwE HiErrorInvalidArgument

-- | Evaluator for HiFunMkDir. Returns HiActionMkDir
evalMkDir :: HiMonad m => HiValue -> ExceptT HiError m HiValue
evalMkDir (HiValueString s) = return $ HiValueAction $ HiActionMkDir $ T.unpack s
evalMkDir _ = throwE HiErrorInvalidArgument

-- | Evaluator for HiFunChDir. Returns HiActionChDir
evalChDir :: HiMonad m => HiValue -> ExceptT HiError m HiValue
evalChDir (HiValueString s) = return $ HiValueAction $ HiActionChDir $ T.unpack s
evalChDir _ = throwE HiErrorInvalidArgument

-- | Evaluator for HiFunTime. If the input contains invalid time, returns null, otherwise the parsed time
evalTime :: HiMonad m => HiValue -> ExceptT HiError m HiValue
evalTime (HiValueString s) = return $ (maybe HiValueNull HiValueTime) $ readMaybe $ T.unpack s
evalTime _ = throwE HiErrorInvalidArgument

-- | Evaluator for HiFunRand. Returns random numbers in given range
evalRand :: HiMonad m => HiValue -> HiValue -> ExceptT HiError m HiValue
evalRand (HiValueNumber l) (HiValueNumber r)
  | checkRationalToInt l && checkRationalToInt r && l <= r = return $ HiValueAction $ HiActionRand (truncate l) (truncate r)
  | otherwise = throwE HiErrorInvalidArgument
evalRand _ _ = throwE HiErrorInvalidArgument

-- | Evaluator for HiFunEcho. Returns HiActionEcho
evalEcho :: HiMonad m => HiValue -> ExceptT HiError m HiValue
evalEcho (HiValueString s) = return $ HiValueAction $ HiActionEcho s
evalEcho _ = throwE HiErrorInvalidArgument

-- | Evaluator for HiFunCount. Returns dictionary with counts of each elements of `string like` input
evalCount :: HiMonad m => HiValue -> ExceptT HiError m HiValue
evalCount (HiValueString s) = evalCountCommon (\c -> (T.pack [c], 1)) (first HiValueString) $ T.unpack s
evalCount (HiValueBytes b) = evalCountCommon (\c -> (toRational $ ord c, 1)) (first HiValueNumber) $ C.unpack b
evalCount (HiValueList l) = evalCountCommon (,1) id $ toList l
evalCount _ = throwE HiErrorInvalidArgument

-- | Helper for counter
evalCountCommon :: HiMonad m => (a -> (b, Integer)) -> ((b, Integer) -> (HiValue, Integer)) -> [a] -> ExceptT HiError m HiValue
evalCountCommon f1 f2 l = return $ HiValueDict $ M.map (HiValueNumber . toRational) $ M.fromListWith (+) $ map (f2 . f1) l

-- | Evaluator for HiFunKeys. Returns keys of dictionary
evalKeys :: HiMonad m => HiValue -> ExceptT HiError m HiValue
evalKeys (HiValueDict d) = return $ HiValueList $ Seq.sort $ Seq.fromList $ M.keys d
evalKeys _ = throwE HiErrorInvalidArgument

-- | Evaluator for HiFunValues. Returns values of dictionary
evalValues :: HiMonad m => HiValue -> ExceptT HiError m HiValue
evalValues (HiValueDict d) = return $ HiValueList $ Seq.sort $ Seq.fromList $ M.elems d
evalValues _ = throwE HiErrorInvalidArgument

-- | Evaluator for HiFunInvert. Returns inverted dictionary:
-- | invert({ "x": 1, "y" : 2, "z": 1 }) evaluates to { 1: [ "z", "x" ], 2: ["y"] }
evalInvert :: HiMonad m => HiValue -> ExceptT HiError m HiValue
evalInvert (HiValueDict d) =
  return $ HiValueDict $ M.map HiValueList $ M.fromListWith (Seq.><) $ map (\(a, b) -> (b, Seq.fromList [a])) $ M.toList d
evalInvert _ = throwE HiErrorInvalidArgument

-- | Evaluator for unary operations
evalUnary :: HiMonad m => (HiValue -> ExceptT HiError m HiValue) -> [HiExpr] -> ExceptT HiError m HiValue
evalUnary f l
  | length l == 1 = (eval' $ head l) >>= f
  | otherwise = throwE HiErrorArityMismatch

-- | Evaluator for binary operations
evalBinary :: HiMonad m => (HiValue -> HiValue -> ExceptT HiError m HiValue) -> [HiExpr] -> ExceptT HiError m HiValue
evalBinary f l
  | length l == 2 = (eval' $ head l) >>= \a -> (eval' $ last l) >>= f a
  | otherwise = throwE HiErrorArityMismatch

-- | Evaluator for lazy binary operations
evalBinaryLazy :: HiMonad m => ([HiExpr] -> ExceptT HiError m HiValue) -> [HiExpr] -> ExceptT HiError m HiValue
evalBinaryLazy f args
  | length args == 2 = f args
  | otherwise = throwE HiErrorArityMismatch

-- | Evaluator for string methods: slice and indexing
evalStringSeqMethod :: HiMonad m => (a -> Int) -> (Int -> ExceptT HiError m HiValue) -> (Int -> Int -> ExceptT HiError m HiValue) -> a -> [HiExpr] -> ExceptT HiError m HiValue
evalStringSeqMethod getLength getIndex slice s args
  | length args == 1 = evalGetByIndexCommon getLength getIndex s $ head args
  | length args == 2 = evalSliceCommon getLength slice s (head args) (last args)
  | otherwise = throwE HiErrorArityMismatch

-- | Evaluator for indexing
evalGetByIndexCommon :: HiMonad m => (a -> Int) -> (Int -> ExceptT HiError m HiValue) -> a -> HiExpr -> ExceptT HiError m HiValue
evalGetByIndexCommon getLength getIndex s expr = eval' expr >>= getByIndexCommon getLength getIndex s

-- | Helper for indexing
getByIndexCommon :: HiMonad m => (a -> Int) -> (Int -> ExceptT HiError m HiValue) -> a -> HiValue -> ExceptT HiError m HiValue
getByIndexCommon getLength getIndex s (HiValueNumber n)
  | denominator n == 1 = do
    let index = fromInteger $ numerator n
    if (index < 0 || index >= getLength s)
      then return HiValueNull
      else getIndex index
  | otherwise = throwE HiErrorInvalidArgument
getByIndexCommon _ _ _ _ = throwE HiErrorInvalidArgument

-- | Evaluator for slicing
evalSliceCommon :: HiMonad m => (a -> Int) -> (Int -> Int -> ExceptT HiError m HiValue) -> a -> HiExpr -> HiExpr -> ExceptT HiError m HiValue
evalSliceCommon getLength slice s left right = eval' left >>= \l -> eval' right >>= getSliceCommon getLength slice s l

-- | Helper for slicing
getSliceCommon :: HiMonad m => (a -> Int) -> (Int -> Int -> ExceptT HiError m HiValue) -> a -> HiValue -> HiValue -> ExceptT HiError m HiValue
getSliceCommon getLength slice s (HiValueNumber l) (HiValueNumber r) = getIndexForSlice l s getLength >>= \from -> getIndexForSlice r s getLength >>= slice from
getSliceCommon getLength slice s (HiValueNumber l) HiValueNull = getSliceCommon getLength slice s (HiValueNumber l) $ HiValueNumber $ toRational $ getLength s
getSliceCommon getLength slice s HiValueNull (HiValueNumber r) = getSliceCommon getLength slice s (HiValueNumber 0) (HiValueNumber r)
getSliceCommon getLength slice s HiValueNull HiValueNull = getSliceCommon getLength slice s (HiValueNumber 0) $ HiValueNumber $ toRational $ getLength s
getSliceCommon _ _ _ _ _ = throwE HiErrorInvalidArgument

-- | Helper for slicing. Returns corrected index
getIndexForSlice :: (Ord a, Num a, Monad m) => Rational -> t -> (t -> a) -> ExceptT HiError m a
getIndexForSlice i s getLength
  | denominator i == 1 = do
    let index = fromInteger $ numerator i
    let pos = if index < 0 then getLength s + index else index
    return $ if pos < 0 then 0 else pos
  | otherwise = throwE HiErrorInvalidArgument

-- | Evaluator for dictionary queries
evalGetDictValue :: HiMonad m => M.Map HiValue HiValue -> [HiExpr] -> ExceptT HiError m HiValue
evalGetDictValue d args
  | length args == 1 = (eval' $ head args) >>= \query -> return $ fromMaybe HiValueNull (M.lookup query d)
  | otherwise = throwE HiErrorArityMismatch

-- | @`evalAndOr` l r a@ returns l(a) or r(a) depends on a
evalAndOr :: (HiValue -> p) -> (HiValue -> p) -> HiValue -> p
evalAndOr l r a = if a == HiValueNull || a == HiValueBool False then l a else r a

-- | @`evalRepeat` a b value@ returns repeated @a@ @b@ times and wrapped in @value@
evalRepeat :: (Semigroup t, HiMonad m) => t -> Rational -> (t -> a) -> ExceptT HiError m a
evalRepeat a b value
  | denominator b == 1 && b > 0 = return $ value $ stimes (numerator b) a
  | otherwise = throwE HiErrorInvalidArgument
  
-- | Helpful methods to calculate something ...

getTextIndex :: HiMonad m => T.Text -> Int -> ExceptT HiError m HiValue
getTextIndex s ind = return $ HiValueString $ T.singleton $ T.index s ind

getSeqIndex :: HiMonad m => Seq.Seq b -> Int -> ExceptT HiError m b
getSeqIndex s ind = return $ Seq.index s ind

getBytesIndex :: HiMonad m => C.ByteString -> Int -> ExceptT HiError m HiValue
getBytesIndex b ind = return $ HiValueNumber $ toRational $ ord $ C.index b ind

getTextSlice :: HiMonad m => T.Text -> Int -> Int -> ExceptT HiError m HiValue
getTextSlice s from to = return $ HiValueString $ (T.take $ to - from) $ (T.drop from) s

getSeqSlice :: HiMonad m => Seq.Seq HiValue -> Int -> Int -> ExceptT HiError m HiValue
getSeqSlice s from to = return $ HiValueList $ (Seq.take $ to - from) $ (Seq.drop from) s

getBytesSlice :: HiMonad m => C.ByteString -> Int -> Int -> ExceptT HiError m HiValue
getBytesSlice s from to = return $ HiValueBytes $ (C.take $ to - from) $ (C.drop from) s

checkRationalToInt :: Rational -> Bool
checkRationalToInt n = denominator n == 1 && n < toRational (maxBound :: Int) && n > toRational (minBound :: Int)

rationalToHexChar :: HiMonad m => Rational -> ExceptT HiError m Char
rationalToHexChar r
  | denominator r == 1 = do
    let int = toInteger $ numerator $ r
    if int >= 0 && int <= 255
      then return $ chr $ fromInteger $ int
      else throwE HiErrorInvalidArgument
  | otherwise = throwE HiErrorInvalidArgument
