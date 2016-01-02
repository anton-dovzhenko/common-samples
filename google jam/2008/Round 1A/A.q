//Problem A. Minimum Scalar Product

//lines: read0 `$"2008-R1-A-small-practice.in";
lines: read0 `$"2008-R1-A-large-practice.in";
cases: "I"$lines[0];
lines: 1_lines;

getMinimumScalarProduct: {[lines]
    vector1: "J"$" " vs lines[1];
    vector2: "J"$" " vs lines[2];
    : sum (asc vector1) * desc vector2;
};
result: `long$();
do[cases; result,: getMinimumScalarProduct[lines]; lines: 3_lines];

order: 1;
output: {[minimum] out: "Case #", string[order], ": ", string[minimum]; order +: 1; :out;} each result;
file: `:GJ2008R1A.txt;
file 0: output;