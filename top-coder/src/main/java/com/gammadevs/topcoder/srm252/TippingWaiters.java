package com.gammadevs.topcoder.srm252;

/**
 * 436.65 of 600 -> 354.65 of 600
 * Created by Anton on 10/26/2014.
 */
public class TippingWaiters {

    public int possiblePayments(int bill, int cash) {
        long minTip = (long) (bill / 0.95 - bill);
        if (minTip < bill / 0.95 - bill) {
            minTip++;
        }
        long maxTip = (long) (bill / 0.9 - bill);

        int count = 0;
        for (long i = minTip; i <= maxTip; i++) {
            if ((bill + i) % 5 == 0 && bill + i <= cash) {
                count++;
            }
        }

        return count;
    }
}
