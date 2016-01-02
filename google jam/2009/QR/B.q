\cd /Users/anton/Documents/software-projects/github/common-samples/google jam/2009/QR

lines: read0 `$"B-small-practice.in";
T:"I"$lines[0];
alphabet: `char$ 97 + til 26;
result: ();
lines: 1_lines;

map:();
sinks:();
toFlow:();
sink: 0;

seek: {[i; j; sink]
    $[not sinks[i; j] = `NONE; :`return;];
    sinks[i; j]:: `$alphabet[sink];
    neighbours: ((i-1; j); (i; j-1); (i; j+1); (i+1; j)); 
    toFlowValue: toFlow[i; j];
    $[toFlowValue > -1; seek[neighbours[toFlowValue][0]; neighbours[toFlowValue][1]; sink];];
    flowFrom: neighbours[where[(3 2 1 0) = {toFlow[x[0]; x[1]]} each neighbours]];
    {seek[x[0]; x[1]; sink]} each flowFrom;
    
};

getSinksMap: {[lines]
    input: "I"$" "vs lines[0];   
    H: input[0];
    W: input[1];
    map:: `int$();
    i: 1;
    while[i < 1 + H; map,:  "I"$" "vs lines[i]; i +: 1]; 
    
    map:: W cut map;
    sinks:: W cut (H*W)#`NONE;
    toFlow:: W cut (H*W)#-1;
    sink:: 0;
    
    {
        i: x[0]; 
        j: x[1]; 
        neighbours: ((i-1; j); (i; j-1); (i; j+1); (i+1; j));
        values: ();
        values,: map[neighbours[0; 0]; neighbours[0; 1]];
        values,: map[neighbours[1; 0]; neighbours[1; 1]];
        values,: map[neighbours[2; 0]; neighbours[2; 1]];
        values,: map[neighbours[3; 0]; neighbours[3; 1]];
        minimum: min values;
        $[minimum < map[i;j]; toFlow[i; j]:: values?minimum;];
    
    } each raze ((til H) ,/:\: til W);
    
    
    while[ (W*H) > (raze sinks)?`NONE 
        ; seek[floor ((raze sinks)?`NONE) % W; ((raze sinks)?`NONE) mod W ; sink]
        ; sink +:: 1
    ];
    result ,:: enlist sinks;
    :H+1;
};

do[T; index: getSinksMap[lines]; lines: index _ lines];


output: ();
{
    output,: enlist ("Case #", string[x + 1], ":");
    {output,: " " sv x} each string result x
} each til count result;

file: `:B_small.out;
file 0: output;

