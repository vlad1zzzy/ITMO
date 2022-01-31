module HW1.T6
  ( mcat,
    epart,
  )
where

mcat :: Monoid a => [Maybe a] -> a
mcat [] = mconcat []
mcat (Just x : xs) = mconcat [x, mcat xs]
mcat (_ : xs) = mcat xs

process :: (Monoid a, Monoid b) => Either a b -> (a, b) -> (a, b)
process (Left left) (a, b) = (left <> a, b)
process (Right right) (a, b) = (a, right <> b)

epart :: (Monoid a, Monoid b) => [Either a b] -> (a, b)
epart = foldr process (mempty, mempty)
