module HW1.T2
  ( N (..),
    nFromNatural,
    nToNum,
    nplus,
    nmult,
    nsub,
    ncmp,
    nEven,
    nOdd,
    ndiv,
    nmod,
  )
where

import Numeric.Natural

data N = Z | S N deriving (Show)

nFromNatural :: Natural -> N
nFromNatural 0 = Z
nFromNatural a = S (nFromNatural (a - 1))

nToNum :: Num a => N -> a
nToNum Z     = 0
nToNum (S a) = nToNum a + 1

nplus :: N -> N -> N -- addition
nplus a Z     = a
nplus a (S b) = S (nplus a b)

nmult :: N -> N -> N -- multiplication
nmult _ Z     = Z
nmult a (S b) = nplus (nmult a b) a

nsub :: N -> N -> Maybe N -- subtraction     (Nothing if result is negative)
nsub a Z         = Just a
nsub Z (S _)     = Nothing
nsub (S a) (S b) = nsub a b

ncmp :: N -> N -> Ordering -- comparison      (Do not derive Ord)
ncmp Z Z         = EQ
ncmp _ Z         = GT
ncmp Z _         = LT
ncmp (S a) (S b) = ncmp a b

nEven, nOdd :: N -> Bool    -- parity checking
nEven Z     = True
nEven (S a) = not (nEven a)
nOdd a      = not (nEven a)

nsubForDiv :: N -> N -> N
nsubForDiv a Z = a
nsubForDiv (S a) (S b) = nsubForDiv a b

ndiv :: N -> N -> N         -- integer division
ndiv _ Z = error "Division by zero"
ndiv Z _ = Z
ndiv a b | ncmp a b == LT = Z
ndiv a b                  = S (ndiv (nsubForDiv a b) b)

nmod :: N -> N -> N         -- modulo operation
nmod _ Z = error "Division by zero"
nmod a b = nsubForDiv a (nmult (ndiv a b) b)
