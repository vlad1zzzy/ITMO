module HW0.T4
  ( repeat',
    map',
    fib,
    fac,
  )
where

import Data.Function (fix)
import GHC.Natural

-- | 'repeat' @a@ is an infinite list, with @a@ the value of every element.
repeat' :: a -> [a]
repeat' x = fix (x :)

-- | 'map' @f xs@ is the list obtained by applying @f@ to each element of @xs@
map' :: (a -> b) -> [a] -> [b]
map' f = fix $ \m xs ->
  case xs of
    [] -> []
    y : ys -> f y : m ys

-- | Function to compute n-th Fibonacci number
fib :: Natural -> Natural
fib = fix getFibNum
  where
    getFibNum rec n
      | n <= 0 = 0
      | n <= 2 = 1
      | otherwise = rec (n - 1) + rec (n - 2)

-- | Function to compute the factorial for given number
fac :: Natural -> Natural
fac = fix (\rec n -> if n <= 1 then 1 else n * rec (n - 1))
