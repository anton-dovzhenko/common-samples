package com.gammadevs.inverview.samples.concurrency;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anton on 10/23/2014.
 */
public class FibonacciThread extends Thread {

    private final List<BigInteger> numbers = new ArrayList<>();

    @Override
    public void run() {
        numbers.add(BigInteger.ONE);
        numbers.add(BigInteger.ONE);
        while (!this.isInterrupted()) {
            numbers.add(numbers.get(numbers.size() - 2).add(numbers.get(numbers.size() - 1)));
        }
    }

    public List<BigInteger> getNumbers() {
        if (!this.isInterrupted()) {
            throw new RuntimeException("Calculation is in progress");
        }
        return new ArrayList<>(numbers);
    }

}
