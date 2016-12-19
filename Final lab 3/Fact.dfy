method ComputeFact(n : nat) returns (res : nat)
  requires n > 0;
  ensures res == fact(n);
 {
  res := 1;
  var i := 2;
  while (i <= n) 
  invariant res == fact(i-1) && n >= i -1;   
  decreases n-i;             
  {
    res := res * i;
    i := i + 1;
  }
 }
 
 function fact(x : int): int
  requires x >= 0
  ensures 1 <= fact(x);
  {
    if ( x == 0)
    then
      1
    else
       x * fact(x-1)
  }
