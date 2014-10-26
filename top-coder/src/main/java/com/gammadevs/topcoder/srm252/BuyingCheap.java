package com.gammadevs.topcoder.srm252;

/**
 * 162.67 of 250
 * Created by Anton on 10/26/2014.
 */
public class BuyingCheap {

    public int thirdBestPrice(int[] prices) {

        int lowerBound = -1;
        int min = -1;
        for (int i = 0; i < 3; i++) {
            min = -1;
            for (int j = 0; j < prices.length; j++) {
                if (prices[j] > lowerBound && (min == -1 || prices[j] < min)) {
                    min = prices[j];
                }
            }
            if (min == -1) {
                return -1;
            }
            lowerBound = min;

        }
        return min;
    }
}
