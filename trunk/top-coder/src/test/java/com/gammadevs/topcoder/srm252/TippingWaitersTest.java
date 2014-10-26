package com.gammadevs.topcoder.srm252;

import org.junit.Test;

import static org.junit.Assert.*;

public class TippingWaitersTest {

    @Test
    public void testPossiblePayments() throws Exception {

        TippingWaiters tippingWaiters = new TippingWaiters();
        assertEquals(0, tippingWaiters.possiblePayments(4, 100));
        assertEquals(1, tippingWaiters.possiblePayments(23, 100));
        assertEquals(0, tippingWaiters.possiblePayments(23, 24));
        assertEquals(1, tippingWaiters.possiblePayments(220, 239));
        assertEquals(14440, tippingWaiters.possiblePayments(1234567, 12345678));
        assertEquals(210527, tippingWaiters.possiblePayments(1880000000, 1980000000));
        assertEquals(0, tippingWaiters.possiblePayments(171000000, 179999999));
        assertEquals(0, tippingWaiters.possiblePayments(2000000000, 2000000000));

    }
}