module HW3.Action
  ( HIO (..),
    HiPermission (..),
    PermissionException (..),
  )
where

import Control.Exception (Exception)
import Control.Exception.Base (throwIO)
import Control.Monad (ap)
import qualified Data.ByteString.Char8 as C
import qualified Data.Sequence as Seq
import Data.Set (Set, member)
import qualified Data.Text as T
import Data.Text.Encoding (decodeUtf8')
import Data.Time.Clock.POSIX (getCurrentTime)
import HW3.Base
import System.Directory
import System.Random (randomRIO)

data HiPermission
  = AllowRead
  | AllowWrite
  | AllowTime
  deriving (Eq, Ord, Show)

data PermissionException = PermissionRequired HiPermission deriving (Show)

instance Exception PermissionException

newtype HIO a = HIO {runHIO :: Set HiPermission -> IO a}

instance Monad HIO where
  return a = HIO $ const $ return a
  m >>= k = HIO $ \perm -> runHIO m perm >>= \v -> runHIO (k v) perm

instance Applicative HIO where
  pure = HIO . const . pure
  p <*> q = Control.Monad.ap p q

instance Functor HIO where
  fmap f (HIO r) = HIO $ fmap f . r

instance HiMonad HIO where
  runAction (HiActionRead file) = getRunAction AllowRead (runRead file)
  runAction (HiActionWrite file inner) = getRunAction AllowWrite (C.writeFile file inner >> return HiValueNull)
  runAction (HiActionChDir dir) = getRunAction AllowRead (setCurrentDirectory dir >> return HiValueNull)
  runAction HiActionCwd = getRunAction AllowRead (getCurrentDirectory >>= return . HiValueString . T.pack)
  runAction (HiActionMkDir dir) = getRunAction AllowWrite (createDirectory dir >> return HiValueNull)
  runAction HiActionNow = getRunAction AllowTime (getCurrentTime >>= return . HiValueTime)
  runAction (HiActionRand l r) = HIO (const $ randomRIO (l, r) >>= return . HiValueNumber . toRational)
  runAction (HiActionEcho inner) = getRunAction AllowWrite (putStrLn (T.unpack inner) >> return HiValueNull)

runRead :: String -> IO HiValue
runRead file
  | '.' `elem` file = do
    inner <- C.readFile file
    case decodeUtf8' inner of
      Right r -> return $ HiValueString r
      _ -> return $ HiValueBytes inner
  | otherwise = (listDirectory file) >>= \d -> return $ HiValueList $ Seq.fromList $ map (HiValueString . T.pack) d

getRunAction :: HiPermission -> IO a -> HIO a
getRunAction perm f =
  HIO
    ( \set -> do
        if member perm set
          then f
          else throwIO $ PermissionRequired perm
    )
