module HW2.T5
  ( ExceptState(..),
    EvaluationError(..),
    mapExceptState,
    wrapExceptState,
    joinExceptState,
    modifyExceptState,
    throwExceptState,
    eval,
  )
where

import qualified Control.Monad
import HW2.T1 (Annotated (..), Except (..), mapAnnotated, mapExcept)
import HW2.T2 (wrapExcept)
import HW2.T4 (Expr (..), Prim (..))

data ExceptState e s a = ES {runES :: s -> Except e (Annotated s a)}

-- | Based on given function mapping ExceptState
mapExceptState :: (a -> b) -> ExceptState e s a -> ExceptState e s b
mapExceptState f ES {runES = runner} = ES {runES = mapExcept (mapAnnotated f) . runner}

-- | Wrapping in ExceptState
wrapExceptState :: a -> ExceptState e s a
wrapExceptState a = ES {runES = \s -> (wrapExcept (a :# s))}

-- | Join function for ExceptState
joinExceptState :: ExceptState e s (ExceptState e s a) -> ExceptState e s a
joinExceptState ES {runES = outer} = ES {runES = \s -> joinStateInner (outer s)}
  where
    joinStateInner :: Except e (Annotated s (ExceptState e s a)) -> Except e (Annotated s a)
    joinStateInner (Success (ES {runES = inner} :# s)) = inner s
    joinStateInner (Error e)                           = Error e

-- | Modify function for ExceptState
modifyExceptState :: (s -> s) -> ExceptState e s ()
modifyExceptState f = ES {runES = \s -> Success (() :# (f s))}

-- | Throw function for ExceptState
throwExceptState :: e -> ExceptState e s a
throwExceptState e = ES {runES = \_ -> Error e}

instance Functor (ExceptState e s) where
  fmap = mapExceptState

instance Applicative (ExceptState e s) where
  pure = wrapExceptState
  p <*> q = Control.Monad.ap p q

instance Monad (ExceptState e s) where
  m >>= f = joinExceptState (fmap f m)

data EvaluationError = DivideByZero

-- | Evaluation of expression
eval :: Expr -> ExceptState EvaluationError [Prim Double] Double
eval (Val x)        = pure x
eval (Op (Add a b)) = evalBinary Add (+) a b
eval (Op (Sub a b)) = evalBinary Sub (-) a b
eval (Op (Mul a b)) = evalBinary Mul (*) a b
eval (Op (Abs a))   = evalUnary  Abs (abs) a
eval (Op (Sgn a))   = evalUnary  Sgn (signum) a
eval (Op (Div a b)) =
  eval a >>= \a' ->
    eval b >>= \b' ->
      if (b' == 0)
        then throwExceptState DivideByZero
        else mapExceptState (\_ -> (/) a' b') (modifyExceptState (\list -> Div a' b' : list))

-- | Evaluation of binary expression
evalBinary :: (Double -> Double -> Prim Double) -> (Double -> Double -> b) -> Expr -> Expr -> ExceptState EvaluationError [Prim Double] b
evalBinary t op a b = eval a >>= \a' -> eval b >>= \b' -> mapExceptState (\_ -> op a' b') (modifyExceptState (\list -> t a' b' : list))

-- | Evaluation of unary expression
evalUnary :: (Double -> Prim Double) -> (Double -> b) -> Expr -> ExceptState EvaluationError [Prim Double] b
evalUnary t op a = eval a >>= \a' -> mapExceptState (\_ -> op a') (modifyExceptState (\list -> t a' : list))
