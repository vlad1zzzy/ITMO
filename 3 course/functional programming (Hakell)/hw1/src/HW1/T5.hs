module HW1.T5
  ( splitOn,
    joinWith,
  )
where

import Data.List.NonEmpty (NonEmpty (..))

separate :: Eq a => a -> a -> NonEmpty [a] -> NonEmpty [a]
separate separator current (x :| xs) | current == separator = [] :| (x : xs)
separate _ current (x :| xs)                                = (current : x) :| xs

splitOn :: (Eq a) => a -> [a] -> NonEmpty [a]
splitOn separator = foldr (separate separator) ([] :| [])

join :: a -> [a] -> [a] -> [a]
join separator x part = x ++ separator : part

joinWith :: a -> NonEmpty [a] -> [a]
joinWith separator list = init (foldr (join separator) [] list)
