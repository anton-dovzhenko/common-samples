// Returns dictionary of element occurences in the list
// @x - arbitrary list
.math.freq: {key[x]!count each value x:group x};


// Returns all permitations for numbers 0, 1, .., x - 2, x - 1
// @x - integer
.math.perm: {(x-1){raze y,/:'x except/:y}[til x]/til x};