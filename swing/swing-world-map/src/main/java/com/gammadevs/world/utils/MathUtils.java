package com.gammadevs.world.utils;

public final class MathUtils {

    private MathUtils() {}

    public static double getBoundedValue(double value, double lowerLimit, double upperLimit) {
        if (value < lowerLimit) {
            return lowerLimit;
        }
        if (value > upperLimit) {
            return upperLimit;
        }
        return value;
    }

}
