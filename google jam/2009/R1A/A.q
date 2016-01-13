number: 82;
base: 10;
result: `int$();
multiplier: base;

while[number > 0
    ; result,: `int$number mod multiplier
    ; number-:(number mod multiplier) * multiplier % base
    ; multiplier *: base
]