module HW0.T5
  ( Nat,
    nz,
    ns,
    nplus,
    nmult,
    nFromNatural,
    nToNum,
  )
where

import GHC.Natural (Natural)

type Nat a = (a -> a) -> a -> a

-- | Representation of first Nat
nz :: Nat a
nz _ n = n

-- | Function to get next Nat
ns :: Nat a -> Nat a
ns f a b = a (f a b)

-- | Summary and multiplication of Nat's
nplus, nmult :: Nat a -> Nat a -> Nat a
nplus a b f x = a f (b f x)
nmult a b = a . b

-- | Converter from Natural to Nat
nFromNatural :: Natural -> Nat a
nFromNatural 0 = nz
nFromNatural natural = ns (nFromNatural (natural - 1))

-- | Converter from Nat to Num
nToNum :: Num a => Nat a -> a
nToNum n = n (+ 1) 0
