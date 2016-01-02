\cd /Users/anton/Documents/software-projects/github/common-samples/google jam/2009/QR

lines: read0 `$"C-test.in";
N: "I"$lines[0];
message: "welcome to code jam";
symbols: distinct message;

lines: 1_ lines;

index: 1;
matches: ();
indexes: ();
result: ();

{
    line: x;
    matches:: {[line; i] where[line = message[i]]}[line] each til count message;
    indexes:: matches[0];
    permutations: (count matches[0])#1;
    index:: 1;  
    do[count[message] - 1; 
        prevPerm: permutations;
        permutations: {indexes[x] < matches[index]} each til count indexes; 
        permutations: permutations * prevPerm;
        permutations: {[permutations; x] sum permutations[;x]}[permutations] til count matches[index]; 
        indexes:: matches[index][where[permutations > 0]]; 
        permutations: permutations[where[permutations > 0]];
        index +: 1;
    ];
    result,:: $[0 < count permutations; prd permutations; 0];
} each lines;

output: ();
{ output,: enlist ("Case #", string[x + 1], ": ", (-4#"0000", string[result[x] mod 10000]) ); } each til count result;
