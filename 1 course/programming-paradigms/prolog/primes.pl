divisible(N, D) :- 0 is N mod D.
divisible(N, D) :-	D1 is D + 1, N > D1, divisible(N, D1).

prime(2) :- true.
prime(N):-	D is 2, N > 1, not(divisible(N, D)).
						
composite(1) :- false.
composite(N) :-	not(prime(N)).

sorted([N]) :- true.
sorted([F, S | T]) :- F =< S, sorted([S | T]).

list_to_num([], 1) :- true.
list_to_num([H | T], N) :- list_to_num(T, R), N is H * R.

num_to_list(N, D, []) :- 1 is N.
num_to_list(N, D, [H | T]) :- 0 is N mod D, H is D, num_to_list(div(N, D), D, T).
num_to_list(N, D, R) :- num_to_list(N, D + 1, R).

prime_divisors(1, []) :- true, !.
prime_divisors(N, D) :- integer(N), num_to_list(N, 2, D), !.
prime_divisors(N, D) :- sorted(D), list_to_num(D, N).

unique_list([],[]) :- true.
unique_list([F], [F]) :- true.
unique_list([F, S | T], [H1 | T1]) :- F = S, unique_list([S | T], [H1 | T1]).
unique_list([F, S | T], [H1 | T1]) :- H1 is F, unique_list([S | T], T1).

unique_prime_divisors(A, B) :- num_to_list(A, 2, B1), unique_list(B1, B), !.