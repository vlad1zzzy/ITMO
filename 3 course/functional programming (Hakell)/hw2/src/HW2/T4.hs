module HW2.T4
  ( State (..),
    Prim (..),
    Expr (..),
    mapState,
    wrapState,
    joinState,
    modifyState,
    eval,
  )
where

import qualified Control.Monad
import HW2.T1 (Annotated (..), mapAnnotated)

data State s a = S {runS :: s -> Annotated s a}

-- | Based on given function mapping State
mapState :: (a -> b) -> State s a -> State s b
mapState f S {runS = runner} = S {runS = mapAnnotated f . runner}

-- | Wrapping in State
wrapState :: a -> State s a
wrapState a = S {runS = \s -> a :# s}

-- | Join function for State
joinState :: State s (State s a) -> State s a
joinState S {runS = outer} = S {runS = \s -> joinStateInner (outer s)}
  where
    joinStateInner :: Annotated s (State s a) -> Annotated s a
    joinStateInner (S {runS = inner} :# s) = inner s

-- | Modify function for State
modifyState :: (s -> s) -> State s ()
modifyState f = S {runS = \s -> (() :# (f s))}

instance Functor (State s) where
  fmap = mapState

instance Applicative (State s) where
  pure = wrapState
  p <*> q = Control.Monad.ap p q

instance Monad (State s) where
  m >>= f = joinState (fmap f m)

data Prim a
  = Add a a -- (+)
  | Sub a a -- (-)
  | Mul a a -- (*)
  | Div a a -- (/)
  | Abs a   -- abs
  | Sgn a   -- signum

data Expr = Val Double | Op (Prim Expr)

instance Num Expr where
  x + y         = Op (Add x y)
  x - y         = Op (Sub x y)
  x * y         = Op (Mul x y)
  abs x         = Op (Abs x)
  signum x      = Op (Sgn x)
  fromInteger x = Val (fromInteger x)

instance Fractional Expr where
  x / y          = Op (Div x y)
  fromRational x = Val (fromRational x)

-- | Evaluation of expression
eval :: Expr -> State [Prim Double] Double
eval (Val x)        = pure x
eval (Op (Add a b)) = evalBinary Add (+) a b
eval (Op (Sub a b)) = evalBinary Sub (-) a b
eval (Op (Mul a b)) = evalBinary Mul (*) a b
eval (Op (Div a b)) = evalBinary Div (/) a b
eval (Op (Abs a))   = evalUnary  Abs (abs) a
eval (Op (Sgn a))   = evalUnary  Sgn (signum) a

-- | Evaluation of binary expression
evalBinary :: (Double -> Double -> Prim Double) -> (Double -> Double -> b) -> Expr -> Expr -> State [Prim Double] b
evalBinary t op a b =
  eval a >>= \a' ->
    eval b >>= \b' ->
      mapState (\_ -> op a' b') (modifyState (\list -> t a' b' : list))

-- | Evaluation of unary expression
evalUnary :: (Double -> Prim Double) -> (Double -> b) -> Expr -> State [Prim Double] b
evalUnary t op a = eval a >>= \a' -> mapState (\_ -> op a') (modifyState (\list -> t a' : list))
