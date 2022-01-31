module HW0.T2
  ( Not,
    doubleNeg,
    reduceTripleNeg,
  )
where

import Data.Void (Void)

type Not a = a -> Void

-- | Double negation for given value
doubleNeg :: a -> Not (Not a)
doubleNeg a b = b a

-- | Reducer of triple negation
reduceTripleNeg :: Not (Not (Not a)) -> Not a
reduceTripleNeg a b = a (doubleNeg b)
