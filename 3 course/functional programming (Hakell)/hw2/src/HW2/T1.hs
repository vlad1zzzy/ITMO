module HW2.T1
  ( Option (..),
    Pair (..),
    Quad (..),
    Annotated (..),
    Except (..),
    Prioritised (..),
    Stream (..),
    List (..),
    Fun (..),
    Tree (..),
    mapOption,
    mapPair,
    mapQuad,
    mapAnnotated,
    mapExcept,
    mapPrioritised,
    mapStream,
    mapList,
    mapFun,
    mapTree,
  )
where

data Option a = None | Some a

data Pair a = P a a

data Quad a = Q a a a a

data Annotated e a = a :# e

infix 0 :#

data Except e a = Error e | Success a

data Prioritised a = Low a | Medium a | High a

data Stream a = a :> Stream a

infixr 5 :>

data List a = Nil | a :. List a

infixr 5 :.

data Fun i a = F (i -> a)

data Tree a = Leaf | Branch (Tree a) a (Tree a)

-- | Implementation of
-- |        mapF :: (a -> b) -> (F a -> F b)
-- | where
-- |        mapF id  ≡  id
-- |        mapF f ∘ mapF g   ≡  mapF (f ∘ g)
-- | and 'F' is defined data above

-- | Based on given function mapping Option
mapOption :: (a -> b) -> (Option a -> Option b)
mapOption _ None = None
mapOption f (Some a) = Some (f a)

-- | Based on given function mapping Pair
mapPair :: (a -> b) -> (Pair a -> Pair b)
mapPair f (P a b) = P (f a) (f b)

-- | Based on given function mapping Quad
mapQuad :: (a -> b) -> (Quad a -> Quad b)
mapQuad f (Q a b c d) = Q (f a) (f b) (f c) (f d)

-- | Based on given function mapping Annotated
mapAnnotated :: (a -> b) -> (Annotated e a -> Annotated e b)
mapAnnotated f (a :# e) = (f a) :# e

-- | Based on given function mapping Except
mapExcept :: (a -> b) -> (Except e a -> Except e b)
mapExcept _ (Error a)   = (Error a)
mapExcept f (Success a) = Success (f a)

-- | Based on given function mapping Prioritised
mapPrioritised :: (a -> b) -> (Prioritised a -> Prioritised b)
mapPrioritised f (Low a)    = Low (f a)
mapPrioritised f (Medium a) = Medium (f a)
mapPrioritised f (High a)   = High (f a)

-- | Based on given function mapping Stream
mapStream :: (a -> b) -> (Stream a -> Stream b)
mapStream f (a :> b) = (f a) :> (mapStream f b)

-- | Based on given function mapping List
mapList :: (a -> b) -> (List a -> List b)
mapList _ Nil      = Nil
mapList f (a :. b) = (f a) :. (mapList f b)

-- | Based on given function mapping Fun
mapFun :: (a -> b) -> (Fun i a -> Fun i b)
mapFun f (F a) = F (f . a)

-- | Based on given function mapping Tree
mapTree :: (a -> b) -> (Tree a -> Tree b)
mapTree _ Leaf           = Leaf
mapTree f (Branch l a r) = Branch (mapTree f l) (f a) (mapTree f r)
