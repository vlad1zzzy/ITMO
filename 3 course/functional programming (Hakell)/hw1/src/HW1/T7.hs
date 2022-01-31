{-# LANGUAGE InstanceSigs #-}

module HW1.T7
  ( DotString (..),
    ListPlus (..),
    Inclusive (..),
    Fun (..),
  )
where

import Data.Semigroup ()

data ListPlus a = a :+ ListPlus a | Last a deriving (Show)

infixr 5 :+

instance Semigroup (ListPlus a) where
  (<>) :: ListPlus a -> ListPlus a -> ListPlus a
  (<>) (Last a) (Last b) = a :+ Last b
  (<>) (a :+ aa) (Last b) = a :+ (aa <> Last b)
  (<>) (Last a) (b :+ bb) = a :+ (Last b <> bb)
  (<>) (a :+ aa) (b :+ bb) = a :+ (aa <> (b :+ bb))

newtype DotString = DS String deriving (Show)

instance Semigroup DotString where
  (<>) :: DotString -> DotString -> DotString
  (<>) (DS a) (DS "") = DS a
  (<>) (DS "") (DS b) = DS b
  (<>) (DS a) (DS b) = DS (a ++ "." ++ b)

instance Monoid DotString where
  mempty = DS ""

data Inclusive a b = This a | That b | Both a b deriving (Show)

instance (Semigroup a, Semigroup b) => Semigroup (Inclusive a b) where
  (<>) :: Inclusive a b -> Inclusive a b -> Inclusive a b
  (<>) (This a) (This b) = This (a <> b)
  (<>) (This a) (That b) = Both a b
  (<>) (This a) (Both b c) = Both (a <> b) c
  (<>) (That a) (This b) = Both b a
  (<>) (That a) (That b) = That (a <> b)
  (<>) (That a) (Both b c) = Both b (a <> c)
  (<>) (Both a b) (This c) = Both (a <> c) b
  (<>) (Both a b) (That c) = Both a (b <> c)
  (<>) (Both a b) (Both c d) = Both (a <> c) (b <> d)

newtype Fun a = F (a -> a)

instance Semigroup (Fun a) where
  (<>) :: Fun a -> Fun a -> Fun a
  (<>) (F a) (F b) = F (a . b)

instance Monoid (Fun a) where
  mempty = F id
