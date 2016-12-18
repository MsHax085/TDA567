method Q3(n0 : int, m0 : int) returns (res : int)
ensures res == n0 * m0;
{
  var n, m : int;
  res := 0;
  if (n0 >= 0) 
       {n,m := n0, m0;} 
  else 
       {n,m := -n0, -m0;}
  while (0 < n)
  invariant n >= 0;
  invariant n0 >= 0 ==> res == (n0 - n) * m;
  invariant n0 < 0 ==> res == (-n0 - n) * m;
  decreases n;
  { 
    res := res + m; 
    n := n - 1; 
  }
}


------------------

{n0 = 5, m0 = 3}

0 < 5
res = 0 + 3 = 3
n = 5 - 1 = 4

0 < 4
res = 3 + 3 = 6
n = 4 - 1 = 3

0 < 3
res = 6 + 3 = 9
n = 3 - 1 = 2

0 < 2
res = 9 + 3 = 12
n = 2 - 1 = 1

0 < 1
res = 12 + 3 = 15
n = 1 - 1 = 0

------------------

S1 =	res := 0;
		if (n0 >= 0) then (n := n0; m := m0) else (n := -n0; m := -m0)
S =		res := res + m; n := n - 1;

I =		(n >= 0) &&
		(n0 >= 0 ==> res == (n0 - n) * m) &&
		(n0 < 0 ==> res == (-n0 - n) * m);
		
wp(S1, while(0 < n), S);

1. Prove wp(S1, I) - I holds in S1

wp (res := 0; if (n0 >= 0) then (n := n0; m := m0) else (n := -n0; m := -m0); I)
wp (res := 0; (n0 >= 0 ==> wp(n := n0; m := m0; I)) && (n0 < 0 ==> wp(n := -n0; m := -m0, I)));
wp (res := 0; (n0 >= 0 ==> wp(n := n0; wp(m := m0, I))) && (n0 < 0 ==> wp(n := -n0; wp(m := -m0; I))));

wp (res := 0;
	(n0 >= 0 ==> wp(n := n0, wp(m := m0; (n >= 0 && (n0 >= 0 ==> res == (n0 - n) * m) && (n0 < 0 ==> res == (-n0 - n) * m))))) &&
	(n0 < 0 ==> wp(n := -n0; wp(m := -m0; (n >= 0 && (n0 >= 0 ==> res == (n0 - n) * m) && (n0 < 0 ==> res == (-n0 - n) * m))))));
	
wp (res := 0;
	(n0 >= 0 ==> wp(n := n0, (n >= 0 && (n0 >= 0 ==> res == (n0 - n) * m0) && (n0 < 0 ==> res == (-n0 - n) * m0)))) &&
	(n0 < 0 ==> wp(n := -n0; (n >= 0 && (n0 >= 0 ==> res == (n0 - n) * -m0) && (n0 < 0 ==> res == (-n0 - n) * -m0)))));
	
wp (res := 0;
	(n0 >= 0 ==> (n0 >= 0 && (n0 >= 0 ==> res == (n0 - n0) * m0) && (n0 < 0 ==> res == (-n0 - n0) * m0))) &&
	(n0 < 0 ==> (-n0 >= 0 && (-n0 >= 0 ==> res == (n0 + n0) * -m0) && (n0 < 0 ==> res == (-n0 + n0) * -m0))));
	
(n0 >= 0 ==> (n0 >= 0 && (n0 >= 0 ==> 0 == (n0 - n0) * m0) && (n0 < 0 ==> 0 == (-n0 - n0) * m0))) &&
(n0 < 0 ==> (-n0 >= 0 && (-n0 >= 0 ==> 0 == (n0 + n0) * -m0) && (n0 < 0 ==> 0 == (-n0 + n0) * -m0)));

(n0 >= 0 ==>
	(n0 >= 0 &&
	(n0 >= 0 ==> 0 == (n0 - n0) * m0) &&
	(n0 < 0 ==> 0 == (-n0 - n0) * m0))) &&
	
(n0 < 0 ==>
	(-n0 >= 0 &&
	(-n0 >= 0 ==> 0 == (n0 + n0) * -m0) &&
	(n0 < 0 ==> 0 == (-n0 + n0) * -m0)))
	

(n0 >= 0 ==>
	(n0 >= 0 &&								(true)
	(n0 >= 0 ==> 0 == 0) &&					(true)
	(n0 < 0 ==> 0 == -2 * n0 * m0))) &&		(true)
	
(n0 < 0 ==>
	(-n0 >= 0 &&							(true)
	(n0 >= 0 ==> 0 == -2 * n0 * m0) &&		(true)
	(n0 < 0 ==> 0 == 0)))					(true)
	
Thus true.

2. Prove wp(S, I) - I holds in S

wp (res := res + m; n := n - 1; I);
wp (res := res + m; wp(n := n - 1; I));
wp (res := res + m;
	wp (n := n - 1;
	(n >= 0 && (n0 >= 0 ==> res == (n0 - n) * m) && (n0 < 0 ==> res == (-n0 - n) * m))));

(n - 1 >= 0 && (n0 >= 0 ==> (res + m) == (n0 - (n - 1)) * m) && (n0 < 0 ==> (res + m) == (-n0 - (n - 1)) * m));
(n >= 1 && (n0 >= 0 ==> (res + m) == (n0 - n + 1) * m) && (n0 < 0 ==> (res + m) == (-n0 - n + 1) * m));
(n >= 1 && (n0 >= 0 ==> (res + m) == (n0 - n) * m + m) && (n0 < 0 ==> (res + m) == (-n0 - n) * m + m));
(n > 0 && (n0 >= 0 ==> res == (n0 - n) * m) && (n0 < 0 ==> res == (-n0 - n) * m));

I && (loop condition) = I && (0 < n)
(n >= 0 && (n0 >= 0 ==> res == (n0 - n) * m) && (n0 < 0 ==> res == (-n0 - n) * m)) && (0 < n);
(n >= 0) && (0 < n) = 0 < n;
(n > 0 && (n0 >= 0 ==> res == (n0 - n) * m) && (n0 < 0 ==> res == (-n0 - n) * m));

wp(S, I) <==> I && (loop condition);

Thus:
I && (loop condition) ==> wp (S, I);

3. Prove loop condition

I && (loop condition) ==> (n decreases) > 0

True since loop condition n > 0

4. Decreasing n

wp (n2 := n; res := res + m; n := n - 1; n2 > n);
wp (n2 := n; wp(res := res + m; wp(n := n - 1; n2 > n))=;
wp (n2 := n; wp(res := res + m; n2 > n - 1));
wp (n2 := n; n2 > n - 1);
(n > n - 1);

Always true.

5. Ensure (res == n0 * m0) holds

I && !(loop condition) ==> (n >= 0 && (n0 >= 0 ==> res == (n0 - n) * m) && (n0 < 0 ==> res == (-n0 - n) * m)) && (0 >= n);
I && !(loop condition) ==> (n == 0 && (n0 >= 0 ==> res == (n0) * m) && (n0 < 0 ==> res == (-n0) * m));

(n == 0 && (n0 >= 0 ==> res == (n0 - n) * m) && (n0 < 0 ==> res == (-n0 - n) * m)) ==> (res == n0 * m0)

If n0 >= 0 holds, then m must be m0 and res therefore must be n0 * m0
If n0 < 0 holds, then m must me -m0 and res therefore must be n0 * m0

Because (n0 >= 0) OR (n0 < 0) == TRUE
This must hold: I && !(loop condition) ==> (res == n0 * m0)