module HW2.T3
  ( joinOption,
    joinExcept,
    joinAnnotated,
    joinList,
    joinFun,
  )
where

import HW2.T1 (Annotated (..), Except (..), Fun (..), List (..), Option (..))
import HW2.T2 (catList)

-- | Implementation of
-- |        joinF :: F (F a) -> F a
-- | where
-- |        joinF      (wrapF m)  ≡  m
-- |        joinF (mapF wrapF m)  ≡  m
-- | and 'F' is defined data in HW2.T1

-- | Join function for Option
joinOption :: Option (Option a) -> Option a
joinOption None     = None
joinOption (Some a) = a

-- | Join function for Except
joinExcept :: Except e (Except e a) -> Except e a
joinExcept (Error e)   = Error e
joinExcept (Success a) = a

-- | Join function for Annotated
joinAnnotated :: Semigroup e => Annotated e (Annotated e a) -> Annotated e a
joinAnnotated ((a :# e2) :# e1) = a :# (e1 <> e2)

-- | Join function for List
joinList :: List (List a) -> List a
joinList Nil      = Nil
joinList (a :. b) = catList a (joinList b)

-- | Join function for Fun
joinFun :: Fun i (Fun i a) -> Fun i a
joinFun (F f) = F (\i -> getFun (f i) i)
  where
    getFun :: Fun i a -> (i -> a)
    getFun (F f') = f'
