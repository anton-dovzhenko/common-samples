//Problem B. Milkshakes

lines: read0 `$"2008_R1-B-large-practice.in";
cases: "I"$lines[0];
lines: 1_lines;

vectors:: ();
getMilkShakes: {[lines]
    N: "I"$lines[0];
    cases: "I"$lines[1];
    show "N, cases";
    show (N; cases);
    lines: 2_lines; 
    vectors:: enlist N#0N;
    do[cases
            ; preferences: (2 cut 1_"I"$" " vs lines[0])
            ; preferences[;0]: preferences[;0] - 1
            ; vectors:: distinct raze {[pref] temp: vectors[where[(null vectors[;pref[0]]) | vectors[;pref[0]] = pref[1]]]; temp[;pref[0]]: pref[1]; :temp;} each preferences
            ; lines: 1_lines
            ; show count vectors
    ];
    vectors:: distinct 0^vectors;
    minimum: min sum each vectors;
    vectors:: vectors[where[minimum = sum each vectors]];
    show vectors;
    cases    
};

result: ();
do[cases; toRemove: 2 + getMilkShakes[lines]; lines: toRemove _ lines; result,: enlist(vectors)];
result: 0 ^ (raze result);
order: 1;
output: {[malted] out: "Case #", string[order], ": ", $[0 = count malted; "IMPOSSIBLE"; " " sv string[malted]]; order +: 1; :out;} each result;


file: `:GJ2008R1B_large.txt;
file 0: output;


