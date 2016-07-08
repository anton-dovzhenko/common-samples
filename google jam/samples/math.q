//fibonacci
nextFibonacci: {(x[1]; x[0] + x[1])};
fibonacci: {[index]  (nextFibonacci/)[index; 1 1]};
//test
(89 144) ~ fibonacci 10;

//factorial
factorial: {prd 1 + til x};
 //test
120 = factorial 5;

//Pythagorean triangle
nextTriangle: {1, ({[array;index] :array[index] + array[index+1]; }[x]'[til count[x] - 1]) ,1};
pythagoreanTriangle: {(nextTriangle/)[x;1]};
//test
(pythagoreanTriangle[0] ~ 1)
, (pythagoreanTriangle[1] ~ 1 1)
, (pythagoreanTriangle[2] ~ 1 2 1)
, (pythagoreanTriangle[3] ~ 1 3 3 1)
, (pythagoreanTriangle[4] ~ 1 4 6 4 1)
, (pythagoreanTriangle[5] ~ 1 5 10 10 5 1)
, (pythagoreanTriangle[6] ~ 1 6 15 20 15 6 1)

//Number of occureces in word
getOccurrences: {[word] `occurrences`symbol xdesc 0!select occurrences: count i by symbol from flip enlist[`symbol] ! enlist word};
getOccurrences["aaabbcffffff"] ~ flip `symbol`occurrences ! ("fabc"; 6 3 2 1)
