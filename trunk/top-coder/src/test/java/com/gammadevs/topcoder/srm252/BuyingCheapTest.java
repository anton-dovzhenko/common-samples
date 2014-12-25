package com.gammadevs.topcoder.srm252;

import org.junit.Test;

import static org.junit.Assert.*;

public class BuyingCheapTest {

    @Test
    public void testThirdBestPrice() throws Exception {
        BuyingCheap buyingCheap = new BuyingCheap();
        assertEquals(30, buyingCheap.thirdBestPrice(new int[] {10, 40, 50, 20, 70, 80, 30, 90, 60}));
        assertEquals(30, buyingCheap.thirdBestPrice(new int[] {10, 10, 10, 10, 20, 20, 30, 30, 40, 40}));
        assertEquals(-1, buyingCheap.thirdBestPrice(new int[] {10}));
        assertEquals(-1, buyingCheap.thirdBestPrice(new int[] {80, 90, 80, 90, 80}));
    }
}