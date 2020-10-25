package com.gd.snippet.pi;

/**
 * Created by anton on 26/12/16.
 */
public interface PiCalculator {

    double calculate(long N);

    default void checkN(long N) {
        if (N < 1 || N > Long.MAX_VALUE / 2 - 1) {
            throw new IllegalArgumentException(String.format("%d is out of allowed boundaries", N));
        }
    }

    default double getSequenceValue(long i) {
        return Math.pow(-1, i % 2) / (2 * i + 1);
    }

    default double getSequenceValue2(long i) {
        return Math.pow(-1, i) / (2 * i + 1);
    }

}
