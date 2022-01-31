module HW1.T3
  ( Tree (..),
    tsize,
    tdepth,
    tmember,
    tinsert,
    tFromList,
  )
where

data Tree a = Leaf | Branch Int (Tree a) a (Tree a) deriving (Show)

-- | Size of the tree, O(1).
tsize :: Tree a -> Int
tsize Leaf = 0
tsize (Branch size _ _ _) = size

-- | Depth of the tree.
tdepth :: Tree a -> Int
tdepth Leaf = 0
tdepth (Branch _ l _ r) = max (tdepth l) (tdepth r) + 1

-- | Check if the element is in the tree, O(log n)
tmember :: Ord a => a -> Tree a -> Bool
tmember _ Leaf = False
tmember a (Branch _ _ b _) | a == b = True
tmember a (Branch _ l b _) | a < b  = tmember a l
tmember a (Branch _ _ b r) | a > b  = tmember a r

-- | Insert an element into the tree, O(log n)
tinsert :: Ord a => a -> Tree a -> Tree a
tinsert a Leaf = Branch 1 Leaf a Leaf
tinsert a (Branch size l b r) | a == b = Branch size l a r
tinsert a (Branch _ l b r)
  | a < b =
    let left = tinsert a l
     in Branch (tsize left + tsize r + 1) left b r
  | a > b =
    let right = tinsert a r
     in Branch (tsize l + tsize right + 1) l b right

-- | Build a tree from a list, O(n log n)
tFromList :: Ord a => [a] -> Tree a
tFromList = foldr tinsert Leaf
