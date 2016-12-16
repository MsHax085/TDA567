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

1. Prove wp(S1, I)

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

2. Prove wp(S, I)