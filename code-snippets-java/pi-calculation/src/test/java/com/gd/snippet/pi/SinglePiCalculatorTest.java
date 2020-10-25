package com.gd.snippet.pi;

import org.junit.Test;

import static com.gd.snippet.pi.TestConstants.error;
import static org.junit.Assert.*;

/**
 * Created by anton on 26/12/16.
 */
public class SinglePiCalculatorTest {

    @Test(expected = IllegalArgumentException.class)
    public void calculateLowNTest() {
        new SinglePiCalculator().calculate(0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void calculateHighNTest() {
        new SinglePiCalculator().calculate(Long.MAX_VALUE / 2);
    }

    @Test
    public void calculateTest() throws Exception {
        assertEquals(2.666666666666667, new SinglePiCalculator().calculate(1), error);
        assertEquals(3.466666666666667, new SinglePiCalculator().calculate(2), error);
        assertEquals(2.8952380952380956, new SinglePiCalculator().calculate(3), error);
        assertEquals(2.9760461760461765, new SinglePiCalculator().calculate(5), error);
        assertEquals(3.232315809405594, new SinglePiCalculator().calculate(10), error);
        assertEquals(3.189184782277596, new SinglePiCalculator().calculate(20), error);
        assertEquals(3.1514934010709914, new SinglePiCalculator().calculate(100), error);
        assertEquals(3.1425916543395442, new SinglePiCalculator().calculate(1_000), error);
        assertEquals(3.1416926435905346, new SinglePiCalculator().calculate(10_000), error);
        assertEquals(3.1416026534897203, new SinglePiCalculator().calculate(100_000), error);
        assertEquals(3.1415936535887745, new SinglePiCalculator().calculate(1_000_000), error);
        assertEquals(3.1415931535894743, new SinglePiCalculator().calculate(2_000_000), error);
        assertEquals(3.1415927535897814, new SinglePiCalculator().calculate(10_000_000), error);
    }

}