module HW0.T3
  ( s,
    k,
    i,
    compose,
    contract,
    permute,
  )
where

-- | Combinator s
s :: (a -> b -> c) -> (a -> b) -> (a -> c)
s f g x = f x (g x)

-- | Combinator k
k :: a -> b -> a
k x _ = x

-- | Combinator i
i :: a -> a
i = s k k

-- | Composition of functions
compose :: (b -> c) -> (a -> b) -> (a -> c)
compose = s . k

-- | Contract for function to lower the arity
contract :: (a -> a -> b) -> (a -> b)
contract = s (s . k s) s k

-- | Permutations of arguments inside function
permute :: (a -> b -> c) -> (b -> a -> c)
permute = s (s (k (s (k s) k)) s) (k k)
