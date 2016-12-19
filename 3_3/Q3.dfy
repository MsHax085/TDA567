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