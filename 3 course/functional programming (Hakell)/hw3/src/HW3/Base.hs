{-# LANGUAGE BlockArguments #-}
{-# LANGUAGE DeriveAnyClass #-}
{-# LANGUAGE DeriveGeneric #-}
{-# LANGUAGE DerivingStrategies #-}
{-# LANGUAGE FlexibleInstances #-}
{-# LANGUAGE MonoLocalBinds #-}
{-# LANGUAGE UndecidableInstances #-}

module HW3.Base
  ( HiFun (..),
    HiValue (..),
    HiExpr (..),
    HiError (..),
    HiAction (..),
    HiMonad (..),
    functions,
  )
where

import Codec.Serialise
import Data.Bimap (Bimap, fromList)
import Data.ByteString.Char8 (ByteString)
import Data.Map.Internal (Map)
import Data.Sequence (Seq)
import Data.Text (Text)
import Data.Time.Clock (UTCTime)
import GHC.Generics (Generic)

data HiFun -- function names (e.g. div, sort, length, ...)
  = HiFunDiv
  | HiFunMul
  | HiFunAdd
  | HiFunSub
  | HiFunNot
  | HiFunAnd
  | HiFunOr
  | HiFunLessThan
  | HiFunGreaterThan
  | HiFunEquals
  | HiFunNotLessThan
  | HiFunNotGreaterThan
  | HiFunNotEquals
  | HiFunIf
  | HiFunLength
  | HiFunToUpper
  | HiFunToLower
  | HiFunReverse
  | HiFunTrim
  | HiFunList
  | HiFunRange
  | HiFunFold
  | HiFunPackBytes
  | HiFunUnpackBytes
  | HiFunEncodeUtf8
  | HiFunDecodeUtf8
  | HiFunZip
  | HiFunUnzip
  | HiFunSerialise
  | HiFunDeserialise
  | HiFunRead
  | HiFunWrite
  | HiFunMkDir
  | HiFunChDir
  | HiFunParseTime
  | HiFunRand
  | HiFunEcho
  | HiFunCount
  | HiFunKeys
  | HiFunValues
  | HiFunInvert
  deriving (Show, Eq, Ord, Generic, Serialise)

data HiValue -- values (numbers, booleans, strings, ...)
  = HiValueBool Bool
  | HiValueNumber Rational
  | HiValueFunction HiFun
  | HiValueNull
  | HiValueString Text
  | HiValueList (Seq HiValue)
  | HiValueBytes ByteString
  | HiValueAction HiAction
  | HiValueTime UTCTime
  | HiValueDict (Map HiValue HiValue)
  deriving (Ord, Eq, Show, Generic, Serialise)

data HiExpr -- expressions (literals, function calls, ...)
  = HiExprValue HiValue
  | HiExprApply HiExpr [HiExpr]
  | HiExprRun HiExpr
  | HiExprDict [(HiExpr, HiExpr)]
  deriving (Ord, Eq, Show)

data HiError -- evaluation errors (invalid arguments, ...)
  = HiErrorInvalidArgument
  | HiErrorInvalidFunction
  | HiErrorArityMismatch
  | HiErrorDivideByZero
  deriving (Ord, Eq, Show)

data HiAction -- actions (read, write, ...)
  = HiActionRead FilePath
  | HiActionWrite FilePath ByteString
  | HiActionMkDir FilePath
  | HiActionChDir FilePath
  | HiActionCwd
  | HiActionNow
  | HiActionRand Int Int
  | HiActionEcho Text
  deriving (Ord, Eq, Show, Generic, Serialise)

class Monad m => HiMonad m where
  runAction :: HiAction -> m HiValue

functions :: Bimap String HiFun
functions =
  fromList
    [ ("add", HiFunAdd),
      ("sub", HiFunSub),
      ("mul", HiFunMul),
      ("div", HiFunDiv),
      ("and", HiFunAnd),
      ("or", HiFunOr),
      ("less-than", HiFunLessThan),
      ("greater-than", HiFunGreaterThan),
      ("equals", HiFunEquals),
      ("not-less-than", HiFunNotLessThan),
      ("not-greater-than", HiFunNotGreaterThan),
      ("not-equals", HiFunNotEquals),
      ("not", HiFunNot),
      ("if", HiFunIf),
      ("length", HiFunLength),
      ("to-upper", HiFunToUpper),
      ("to-lower", HiFunToLower),
      ("reverse", HiFunReverse),
      ("trim", HiFunTrim),
      ("list", HiFunList),
      ("range", HiFunRange),
      ("fold", HiFunFold),
      ("pack-bytes", HiFunPackBytes),
      ("unpack-bytes", HiFunUnpackBytes),
      ("encode-utf8", HiFunEncodeUtf8),
      ("decode-utf8", HiFunDecodeUtf8),
      ("zip", HiFunZip),
      ("unzip", HiFunUnzip),
      ("serialise", HiFunSerialise),
      ("deserialise", HiFunDeserialise),
      ("read", HiFunRead),
      ("write", HiFunWrite),
      ("mkdir", HiFunMkDir),
      ("cd", HiFunChDir),
      ("parse-time", HiFunParseTime),
      ("rand", HiFunRand),
      ("echo", HiFunEcho),
      ("count", HiFunCount),
      ("keys", HiFunKeys),
      ("values", HiFunValues),
      ("invert", HiFunInvert)
    ]
