module HW3.Pretty
  ( prettyValue,
  )
where

import qualified Data.Bimap as B
import qualified Data.ByteString.Char8 as C
import Data.Char (ord, toLower)
import Data.Foldable (toList)
import Data.List (intercalate)
import qualified Data.Map as M
import Data.Ratio (denominator, numerator)
import Data.Scientific (FPFormat (Fixed), formatScientific, fromRationalRepetendUnlimited)
import qualified Data.Sequence as Seq
import qualified Data.Text as T
import HW3.Base
import Numeric (showHex)
import Prettyprinter (pretty, viaShow)
import Prettyprinter.Internal.Type (Doc)
import Prettyprinter.Render.Terminal (AnsiStyle)

prettyValue :: HiValue -> Doc AnsiStyle
prettyValue (HiValueNumber r) = do
  let d = denominator r
  if d == 1
    then viaShow (truncate r)
    else do
      let (q, a) = fromRationalRepetendUnlimited r
      case a of
        Nothing -> pretty $ formatScientific Fixed Nothing q
        Just _ -> do
          let n = numerator r
          let (c, e) = quotRem n d
          let ee = if e > 0 then "+ " ++ show e else "- " ++ (show $ abs e)
          if c == 0
            then pretty $ show e ++ "/" ++ show d
            else pretty $ show c ++ " " ++ ee ++ "/" ++ show d
prettyValue (HiValueString s) = viaShow s
prettyValue (HiValueBool b) = pretty $ map toLower (show b)
prettyValue (HiValueNull) = pretty "null"
prettyValue (HiValueTime t) = pretty $ "parse-time(\"" ++ show t ++ "\")"
prettyValue (HiValueList s) = do
  if Seq.length s == 0
    then pretty "[]"
    else pretty $ "[ " <> intercalate ", " (map (\l -> show (prettyValue l)) (toList s)) <> " ]"
prettyValue (HiValueDict d) = do
  if M.size d == 0
    then pretty "{}"
    else pretty $ "{ " <> intercalate ", " (map (\(l, r) -> show (prettyValue l) ++ ": " <> show (prettyValue r)) (M.toList d)) ++ " }"
prettyValue (HiValueAction a) = pretty act
  where
    act = case a of
      HiActionCwd -> "cwd"
      HiActionNow -> "now"
      HiActionRead file -> unaryAction "read" file
      HiActionWrite file inner -> "write(\"" ++ file ++ "\", \"" ++ C.unpack inner ++ "\")"
      HiActionMkDir file -> unaryAction "mkdir" file
      HiActionChDir file -> unaryAction "cd" file
      HiActionRand l r -> "rand(" ++ show l ++ ", " ++ show r ++ ")"
      HiActionEcho inner -> unaryAction "echo" (T.unpack inner)
    unaryAction name file = name ++ "(\"" ++ file ++ "\")"
prettyValue (HiValueBytes b) =
  pretty $ "[# " ++ concatMap (\c -> prettyHex $ showHex (ord c) " ") (C.unpack b) ++ "#]"
  where
    prettyHex :: String -> String
    prettyHex hex = if length hex == 2 then "0" ++ hex else hex
prettyValue (HiValueFunction f) = pretty $ functions B.!> f
