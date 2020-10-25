// Sorts list in ascending order if it os not asc sorted yet
// @ - arbitrary list
.stat.ascIfNotAsc: {
    x: .[#;(`s;x);{[x;y]asc x}[x]];
    if[first[x]>last[x]; x: reverse x];
    x
 };


// Returns percentiles
// @x - arbitrary list
// @y - percentiles within [0;1] range. 0.25 stands for 25th percentile etc.
.stat.pctiles: {.stat.ascIfNotAsc[x]@`int$y*-1+count x};


// Returns histogram as bucket->occurences dictionary
// @x - float list
// @y - step
.stat.hist: {
    x: floor x%y;
    x: count each group x;
    {o: iasc key x; (key[x]@o)!value[x]@o}x
 };


// Returns normal distribution N(mu, sigma) created with Box-Muller method
// Example:
// .stat.getBoxMullerNorm[1000;3;20]
.stat.getBoxMullerNorm: {[N;mu;sigma]
    u1: N?1f;
    u2: N?1f;
    x: sqrt[-2 * log u1] * cos 2 * 3.14159265359 * u2;
    (x*sigma) + mu
 };


