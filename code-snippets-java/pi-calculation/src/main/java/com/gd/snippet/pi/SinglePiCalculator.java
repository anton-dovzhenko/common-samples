package com.gd.snippet.pi;

/**
 * Created by anton on 26/12/16.
 */
public class SinglePiCalculator implements PiCalculator {

    @Override
    public double calculate(long N) {
        checkN(N);
        double pi = 0;
        for (long i = 0; i <= N; i++) {
            pi += getSequenceValue(i);
        }
        return 4 * pi;
    }

}
