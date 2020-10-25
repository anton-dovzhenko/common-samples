package com.gd.snippet.pi;

import org.junit.Test;

import static com.gd.snippet.pi.TestConstants.error;
import static org.junit.Assert.*;

/**
 * Created by anton on 26/12/16.
 */
public class ThreadPiCalculatorTest {

    @Test(expected = IllegalArgumentException.class)
    public void calculateLowNTest() {
        new ThreadPiCalculator(1).calculate(0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void calculateHighNTest() {
        new ThreadPiCalculator(1).calculate(Long.MAX_VALUE / 2);
    }

    @Test
    public void calculate1Thread() throws Exception {
        calculateNThreads(1);
    }

    @Test
    public void calculate2Threads() throws Exception {
        calculateNThreads(2);
    }

    @Test
    public void calculate3Threads() throws Exception {
        calculateNThreads(3);
    }

    @Test
    public void calculate4Threads() throws Exception {
        calculateNThreads(4);
    }

    private void calculateNThreads(int threadCount) {
        assertEquals(2.666666666666667, new ThreadPiCalculator(threadCount).calculate(1), error);
        assertEquals(3.466666666666667, new ThreadPiCalculator(threadCount).calculate(2), error);
        assertEquals(2.8952380952380956, new ThreadPiCalculator(threadCount).calculate(3), error);
        assertEquals(2.9760461760461765, new ThreadPiCalculator(threadCount).calculate(5), error);
        assertEquals(3.232315809405594, new ThreadPiCalculator(threadCount).calculate(10), error);
        assertEquals(3.189184782277596, new ThreadPiCalculator(threadCount).calculate(20), error);
        assertEquals(3.1514934010709914, new ThreadPiCalculator(threadCount).calculate(100), error);
        assertEquals(3.1425916543395442, new ThreadPiCalculator(threadCount).calculate(1_000), error);
        assertEquals(3.1416926435905346, new ThreadPiCalculator(threadCount).calculate(10_000), error);
        assertEquals(3.1416026534897203, new ThreadPiCalculator(threadCount).calculate(100_000), error);
        assertEquals(3.1415936535887745, new ThreadPiCalculator(threadCount).calculate(1_000_000), error);
        assertEquals(3.1415931535894743, new ThreadPiCalculator(threadCount).calculate(2_000_000), error);
        assertEquals(3.1415927535897814, new ThreadPiCalculator(threadCount).calculate(10_000_000), error);
//        assertEquals(3.141592663589326, new ThreadPiCalculator(threadCount).calculate(100_000_000), error);
//        assertEquals(3.1415926545880506, new ThreadPiCalculator(threadCount).calculate(1_000_000_000), error);
    }

}