{-# LANGUAGE TypeOperators #-}

module HW0.T1
  ( type (<->) (Iso),
    flipIso,
    runIso,
    distrib,
    assocPair,
    assocEither,
  )
where

data a <-> b = Iso (a -> b) (b -> a)

-- | Function to flip functions inside given Iso
flipIso :: (a <-> b) -> (b <-> a)
flipIso (Iso f g) = Iso g f

-- | Function to take first function from given Iso
runIso :: (a <-> b) -> (a -> b)
runIso (Iso f _) = f

-- | Distribution rule of Either
distrib :: Either a (b, c) -> (Either a b, Either a c)
distrib (Left a) = (Left a, Left a)
distrib (Right (b, c)) = (Right b, Right c)

-- | Associativity rule of Iso
assocPair :: (a, (b, c)) <-> ((a, b), c)
assocPair = Iso (\(a, (b, c)) -> ((a, b), c)) (\((a, b), c) -> (a, (b, c)))

-- | Associativity rule of Iso of Eithers
assocEither :: Either a (Either b c) <-> Either (Either a b) c
assocEither = Iso l r
  where
    l :: Either a (Either b c) -> Either (Either a b) c
    l (Left a) = Left (Left a)
    l (Right (Left b)) = Left (Right b)
    l (Right (Right c)) = Right c

    r :: Either (Either a b) c -> Either a (Either b c)
    r (Left (Left a)) = Left a
    r (Left (Right b)) = Right (Left b)
    r (Right c) = Right (Right c)
