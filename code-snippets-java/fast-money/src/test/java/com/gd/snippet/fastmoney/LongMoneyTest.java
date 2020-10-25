package com.gd.snippet.fastmoney;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by anton on 3/1/17.
 */
public class LongMoneyTest {

    @Test
    public void addSamePrecision() throws Exception {
        assertEquals(new LongMoney(100, 2), new LongMoney(100, 2).add(new LongMoney(0, 2), 2));
        assertEquals(new LongMoney(100, 2), new LongMoney(50, 2).add(new LongMoney(50, 2), 2));
        assertEquals(new LongMoney(100, 2), new LongMoney(25, 2).add(new LongMoney(75, 2), 2));
    }

    @Test
    public void addDifferentPrecisions() throws Exception {
        assertEquals(new LongMoney(100, 2), new LongMoney(1000, 3).add(new LongMoney(0, 10), 2));
        assertEquals(new LongMoney(100, 2), new LongMoney(500, 3).add(new LongMoney(50, 2), 2));
        assertEquals(new LongMoney(100, 2), new LongMoney(250, 3).add(new LongMoney(7500, 4), 2));
    }

    @Test
    public void addCuttingPrecision() throws Exception {
        assertEquals(new LongMoney(10, 1), new LongMoney(1000, 3).add(new LongMoney(0, 10), 1));
        assertEquals(new LongMoney(122, 1), new LongMoney(525, 2).add(new LongMoney(703, 2), 1));
        assertEquals(new LongMoney(122, 1), new LongMoney(525, 2).add(new LongMoney(704, 2), 1));
        assertEquals(new LongMoney(123, 1), new LongMoney(525, 2).add(new LongMoney(705, 2), 1));
        assertEquals(new LongMoney(123, 1), new LongMoney(525, 2).add(new LongMoney(706, 2), 1));
    }

    @Test
    public void addIncreasingPrecision() throws Exception {
        assertEquals(new LongMoney(10000, 3), new LongMoney(1000, 2).add(new LongMoney(0, 1), 3));
        assertEquals(new LongMoney(12280, 3), new LongMoney(525, 2).add(new LongMoney(703, 2), 3));
        assertEquals(new LongMoney(12290, 3), new LongMoney(525, 2).add(new LongMoney(704, 2), 3));
        assertEquals(new LongMoney(12300, 3), new LongMoney(525, 2).add(new LongMoney(705, 2), 3));
        assertEquals(new LongMoney(12310, 3), new LongMoney(525, 2).add(new LongMoney(706, 2), 3));
    }

    @Test
    public void subtractSamePrecision() throws Exception {
        assertEquals(new LongMoney(0, 2), new LongMoney(100, 2).subtract(new LongMoney(100, 2), 2));
        assertEquals(new LongMoney(-500, 2), new LongMoney(500, 2).subtract(new LongMoney(1000, 2), 2));
        assertEquals(new LongMoney(50, 2), new LongMoney(75, 2).subtract(new LongMoney(25, 2), 2));
    }

    @Test
    public void subtractDifferentPrecisions() throws Exception {
        assertEquals(new LongMoney(100, 2), new LongMoney(1000, 3).subtract(new LongMoney(0, 10), 2));
        assertEquals(new LongMoney(10, 2), new LongMoney(500, 3).subtract(new LongMoney(40, 2), 2));
        assertEquals(new LongMoney(-50, 2), new LongMoney(250, 3).subtract(new LongMoney(7500, 4), 2));
    }

    @Test
    public void subtractCuttingPrecision() throws Exception {
        assertEquals(new LongMoney(10, 1), new LongMoney(1000, 3).subtract(new LongMoney(0, 10), 1));
        assertEquals(new LongMoney(-17, 1), new LongMoney(525, 2).subtract(new LongMoney(703, 2), 1));
        assertEquals(new LongMoney(-17, 1), new LongMoney(525, 2).subtract(new LongMoney(704, 2), 1));
        assertEquals(new LongMoney(-18, 1), new LongMoney(525, 2).subtract(new LongMoney(705, 2), 1));
        assertEquals(new LongMoney(-18, 1), new LongMoney(525, 2).subtract(new LongMoney(706, 2), 1));
    }

    @Test
    public void subtractIncreasingPrecision() throws Exception {
        assertEquals(new LongMoney(1000, 3), new LongMoney(1000, 3).subtract(new LongMoney(0, 10), 3));
        assertEquals(new LongMoney(-1780, 3), new LongMoney(525, 2).subtract(new LongMoney(703, 2), 3));
        assertEquals(new LongMoney(-1790, 3), new LongMoney(525, 2).subtract(new LongMoney(704, 2), 3));
        assertEquals(new LongMoney(-1800, 3), new LongMoney(525, 2).subtract(new LongMoney(705, 2), 3));
        assertEquals(new LongMoney(-1810, 3), new LongMoney(525, 2).subtract(new LongMoney(706, 2), 3));
    }

}