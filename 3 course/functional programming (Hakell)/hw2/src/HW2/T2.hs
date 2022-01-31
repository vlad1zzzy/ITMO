module HW2.T2
  ( distOption,
    distPair,
    distQuad,
    distAnnotated,
    distExcept,
    distPrioritised,
    distStream,
    distList,
    distFun,
    wrapOption,
    wrapPair,
    wrapQuad,
    wrapAnnotated,
    wrapExcept,
    wrapPrioritised,
    wrapStream,
    wrapList,
    wrapFun,
    catList,
  )
where

import HW2.T1

-- | Implementation of
-- |        distF :: (F a, F b) -> F (a, b)
-- |        wrapF :: a -> F a
-- | where
-- |        distF (wrapF a, wrapF b)  ≅  wrapF (a, b)
-- |        distF (p, distF (q, r))   ≅  distF (distF (p, q), r)
-- |        distF (wrapF (), q)       ≅  q
-- |        distF (p, wrapF ())       ≅  p
-- | and 'F' is defined data in HW2.T1

-- | Dist function for Option
distOption :: (Option a, Option b) -> Option (a, b)
distOption (Some a, Some b) = Some (a, b)
distOption (_, _)           = None

-- | Dist function for Pair
distPair :: (Pair a, Pair b) -> Pair (a, b)
distPair (P a b, P c d) = P (a, c) (b, d)

-- | Dist function for Quad
distQuad :: (Quad a, Quad b) -> Quad (a, b)
distQuad (Q a1 b1 c1 d1, Q a2 b2 c2 d2) = Q (a1, a2) (b1, b2) (c1, c2) (d1, d2)

-- | Dist function for Annotated
distAnnotated :: Semigroup e => (Annotated e a, Annotated e b) -> Annotated e (a, b)
distAnnotated (a :# e1, b :# e2) = (a, b) :# (e1 <> e2)

-- | Dist function for Except
distExcept :: (Except e a, Except e b) -> Except e (a, b)
distExcept (Error a, _) = Error a
distExcept (_, Error b) = Error b
distExcept (Success a, Success b) = Success (a, b)

-- | Dist function for Prioritised
distPrioritised :: (Prioritised a, Prioritised b) -> Prioritised (a, b)
distPrioritised (Low a, Low b)       = Low (a, b)
distPrioritised (Low a, Medium b)    = Medium (a, b)
distPrioritised (Medium a, Low b)    = Medium (a, b)
distPrioritised (Medium a, Medium b) = Medium (a, b)
distPrioritised (Medium a, High b)   = High (a, b)
distPrioritised (High a, Medium b)   = High (a, b)
distPrioritised (High a, High b)     = High (a, b)
distPrioritised (High a, Low b)      = High (a, b)
distPrioritised (Low a, High b)      = High (a, b)

-- | Dist function for Stream
distStream :: (Stream a, Stream b) -> Stream (a, b)
distStream (a :> as, b :> bs) = (a, b) :> distStream (as, bs)

-- | Concat 2 List
catList :: List a -> List a -> List a
catList Nil b = b
catList (a :. b) c = a :. (catList b c)

-- | Dist function for List
distList :: (List a, List b) -> List (a, b)
distList (Nil, Nil)  = Nil
distList (_, Nil)    = Nil
distList (Nil, _)    = Nil
distList (a :. b, c) = catList (mapList (makePair a) c) (distList (b, c))
  where
    makePair :: l -> r -> (l, r)
    makePair l r = (l, r)

-- | Dist function for Fun
distFun :: (Fun i a, Fun i b) -> Fun i (a, b)
distFun (F a, F b) = F (\x -> (a x, b x))

-- | Wrapping in Option
wrapOption :: a -> Option a
wrapOption a = Some a

-- | Wrapping in Pair
wrapPair :: a -> Pair a
wrapPair a = P a a

-- | Wrapping in Quad
wrapQuad :: a -> Quad a
wrapQuad a = Q a a a a

-- | Wrapping in Annotated
wrapAnnotated :: Monoid e => a -> Annotated e a
wrapAnnotated a = a :# mempty

-- | Wrapping in Except
wrapExcept :: a -> Except e a
wrapExcept a = Success a

-- | Wrapping in Prioritised
wrapPrioritised :: a -> Prioritised a
wrapPrioritised a = Low a

-- | Wrapping in Stream
wrapStream :: a -> Stream a
wrapStream a = a :> wrapStream a

-- | Wrapping in List
wrapList :: a -> List a
wrapList a = a :. Nil

-- | Wrapping in Fun
wrapFun :: a -> Fun i a
wrapFun a = F (\_ -> a)
