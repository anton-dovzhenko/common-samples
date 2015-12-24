//----- A. Saving the Universe -----------------------------------------------//
//----- CORRECT
lines: read0 `$"A-large-practice.in";
cases: "I"$lines[0];
output: `long$();
lines: 1_lines;

calculateSwitchNumber: {[queries; engines]
    numOfSwitches: 0;
    while[count[queries] > max queries?engines; queries: (max queries?engines)_queries; numOfSwitches +: 1];
    output ,: numOfSwitches
    : numOfSwitches;
};

do[cases; S: "I"$lines[0]; engines: S#1_lines; lines: (S+1)_lines
        ; Q: "I"$lines[0]; queries: Q#1_lines; lines: (Q+1)_lines
        ; calculateSwitchNumber[queries; engines]];

order: 1;
result: {[switches] result: "Case #", string[order], ": " , string[switches]; order +: 1; :result;} each output;

file: `:GJ2008qrA1.txt;
file 0: result;
//flip `engine`query`result ! (engs; quer; result)