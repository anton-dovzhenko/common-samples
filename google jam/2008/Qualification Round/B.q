//----- B. Train Timetable ---------------------------------------------------//
//----- CORRECT
lines: read0 `$"B-large-practice.in";
cases: "I"$lines[0]; lines: 1_lines;
toRemove: 0;
result: ();
T::0;
getSchedule: {[lines]
    T:: "I"$lines[0]; lines: 1_lines;
    temp: " " vs lines[0]; lines: 1_lines;
    NA: "I"$temp[0]; NB: "I"$temp[1];
    
    schedule: flip `start`end`startSt`endSt ! ("T"$(NA#lines)[; 0 + til 5]; "T"$(NA#lines)[; 6 + til 5]; NA#`A; NA#`B);
    lines: NA _ lines;
    schedule,: flip `start`end`startSt`endSt ! ("T"$(NB#lines)[; 0 + til 5]; "T"$(NB#lines)[; 6 + til 5]; NB#`B; NB#`A);
    lines: NB _ lines;
    toRemove:: 2 + NA + NB;
    :schedule;
}; 

calculate: {[schedule]
    schedule: `start xasc schedule;
    schedule: update end: end + 00:01 * T from schedule;
    availableTrainTime:: `A`B ! (`time$(); `time$());
    requiredTrains:: `A`B ! (0; 0);
    trainTravel: {[start; end; startSt; endSt]
        availableTrainTimes: availableTrainTime[startSt];
        isTrainAvailable: 0 < count availableTrainTimes;
        $[isTrainAvailable; isTrainAvailable: start >= availableTrainTimes[0];];
        $[isTrainAvailable; availableTrainTime[startSt]: 1_availableTrainTime[startSt];requiredTrains[startSt] +: 1];
        availableTrainTime[endSt]: asc availableTrainTime[endSt], end;
        show availableTrainTime;
    };
    
    index: 0;
    do[count schedule
        ; trainTravel[schedule[index][`start];schedule[index][`end];schedule[index][`startSt];schedule[index][`endSt]]
        ; index +: 1];
    :enlist(requiredTrains[`A], requiredTrains[`B]);
};

do[cases; result,: calculate[getSchedule[lines]]; lines: toRemove _ lines];
order: 1;
output: {[trains] output: "Case #", string[order], ": " , string[trains[0]], " " , string[trains[1]]; order +: 1; :output;} each result;

file: `:GJ2008qrBLarge.txt;
file 0: output;
