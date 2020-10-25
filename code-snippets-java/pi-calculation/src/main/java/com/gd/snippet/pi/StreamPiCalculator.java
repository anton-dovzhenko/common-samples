package com.gd.snippet.pi;

import java.util.stream.LongStream;

/**
 * Created by anton on 26/12/16.
 */
public class StreamPiCalculator implements PiCalculator {

    @Override
    public double calculate(long N) {
        checkN(N);
        return 4 * LongStream.rangeClosed(0, N).parallel().mapToDouble(this::getSequenceValue).sum();
    }

}
