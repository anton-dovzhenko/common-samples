lines: read0 `$"A-large-practice.in";
input: "I"$" "vs lines[0];
L: input[0];
D: input[1];
N: input[2];
dictionary: lines[1 + til D];
result: `int$();
index: 1 + D;
do[N; result ,: sum dictionary like  ssr[ssr[lines[index]; "("; "["]; ")"; "]"]; index +: 1];

order: 1;
output: {[matches] out: "Case #", string[order], ": ", string[matches]; order +: 1; :out;} each result;

file: `:A_large.out;
file 0: output;