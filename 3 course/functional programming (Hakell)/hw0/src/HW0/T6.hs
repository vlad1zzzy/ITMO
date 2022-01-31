module HW0.T6
  ( a_whnf,
    b_whnf,
    c_whnf,
  )
where

import Data.Char (isSpace)

-- | a, b and c - given values for which 'weak head normal form' is written below
-- | a :: (Either String a, Either String b)
-- | a = distrib (Left ("AB" ++ "CD" ++ "EF"))

-- | b :: [Bool]
-- | b = map isSpace "Hello, World"

-- | c :: String
-- | c = if 1 > 0 || error "X" then "Y" else "Z"

-- | Example of implementation WHNF for given values
a_whnf :: (Either String a, Either String b)
a_whnf = (Left ("AB" ++ "CD" ++ "EF"), Left ("AB" ++ "CD" ++ "EF"))

b_whnf :: [Bool]
b_whnf = False : False : False : map isSpace "llo, World"

c_whnf :: String
c_whnf = ['Y']
