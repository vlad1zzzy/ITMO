module Main where

import Control.Monad.IO.Class (liftIO)
import Data.Either (fromRight)
import Data.Set (fromList)
import HW3.Action
import HW3.Base
import HW3.Evaluator
import HW3.Parser
import HW3.Pretty
import System.Console.Haskeline (InputT, defaultSettings, getExternalPrint, getInputLine, runInputT)

main :: IO ()
main = runInputT defaultSettings loop
  where
    loop :: InputT IO ()
    loop = do
      minput <- getInputLine "hi> "
      case minput of
        Nothing -> return ()
        Just ":q" -> return ()
        Just "" -> loop
        Just input -> do
          printer <- getExternalPrint
          let parsed = fromRight (HiExprValue HiValueNull) (parse input)
--          liftIO $ print parsed
          let evaluated = eval parsed
          r <- liftIO $ runHIO evaluated (fromList [AllowRead, AllowWrite, AllowTime])
          _ <- liftIO $ case r of
            Left e -> printer $ show e
            Right r' -> printer . show $ prettyValue r'
          loop
