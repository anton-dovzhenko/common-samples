package com.gd.snippet.pi;

import org.junit.Test;

import static com.gd.snippet.pi.TestConstants.error;
import static org.junit.Assert.*;

/**
 * Created by anton on 27/12/16.
 */
public class StreamPiCalculatorTest {
    
    @Test(expected = IllegalArgumentException.class)
    public void calculateLowNTest() {
        new StreamPiCalculator().calculate(0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void calculateHighNTest() {
        new StreamPiCalculator().calculate(Long.MAX_VALUE / 2);
    }

    @Test
    public void calculateTest() throws Exception {
        assertEquals(2.666666666666667, new StreamPiCalculator().calculate(1), error);
        assertEquals(3.466666666666667, new StreamPiCalculator().calculate(2), error);
        assertEquals(2.8952380952380956, new StreamPiCalculator().calculate(3), error);
        assertEquals(2.9760461760461765, new StreamPiCalculator().calculate(5), error);
        assertEquals(3.232315809405594, new StreamPiCalculator().calculate(10), error);
        assertEquals(3.189184782277596, new StreamPiCalculator().calculate(20), error);
        assertEquals(3.1514934010709914, new StreamPiCalculator().calculate(100), error);
        assertEquals(3.1425916543395442, new StreamPiCalculator().calculate(1_000), error);
        assertEquals(3.1416926435905346, new StreamPiCalculator().calculate(10_000), error);
        assertEquals(3.1416026534897203, new StreamPiCalculator().calculate(100_000), error);
        assertEquals(3.1415936535887745, new StreamPiCalculator().calculate(1_000_000), error);
        assertEquals(3.1415931535894743, new StreamPiCalculator().calculate(2_000_000), error);
        assertEquals(3.1415927535897814, new StreamPiCalculator().calculate(10_000_000), error);
    }

}