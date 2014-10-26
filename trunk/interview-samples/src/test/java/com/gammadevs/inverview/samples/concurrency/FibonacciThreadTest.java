package com.gammadevs.inverview.samples.concurrency;

import org.junit.Test;

import java.math.BigInteger;
import java.util.List;

import static org.junit.Assert.*;

public class FibonacciThreadTest {

    @Test(expected = RuntimeException.class)
    public void accessNumbersWhileCalculationIsActiveTest() {
        FibonacciThread thread = new FibonacciThread();
        thread.start();
        thread.getNumbers();
    }

    @Test
    public void accessNumbersAfterInterruptionTest() throws InterruptedException {
        FibonacciThread thread = new FibonacciThread();
        thread.start();
        Thread.sleep(1);
        assertFalse(thread.isInterrupted());
        thread.interrupt();
        assertTrue(thread.isInterrupted());
        List<BigInteger> numbers = thread.getNumbers();
        System.out.println(numbers);
    }

}