//fibonacci
nextFibonacci: {(x[1]; x[0] + x[1])};
fibonacci: {[index]  (nextFibonacci/)[index; 1 1]};
//test
(89 144) ~ fibonacci 10;

//factorial
factorial: {prd 1 + til x};
 //test
120 = factorial 5;