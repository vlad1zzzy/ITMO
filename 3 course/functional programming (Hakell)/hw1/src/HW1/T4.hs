module HW1.T4
  ( Tree (..),
    tfoldr,
    treeToList,
  )
where

import HW1.T3

tfoldr :: (a -> b -> b) -> b -> Tree a -> b
tfoldr _ acc Leaf = acc
tfoldr f acc (Branch _ l a r) = tfoldr f (f a (tfoldr f acc r)) l

treeToList :: Tree a -> [a] -- output list is sorted
treeToList = tfoldr (:) []
