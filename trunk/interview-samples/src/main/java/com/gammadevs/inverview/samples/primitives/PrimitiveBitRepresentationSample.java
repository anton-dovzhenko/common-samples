package com.gammadevs.inverview.samples.primitives;

/**
 * Created by Anton on 10/25/2014.
 */
public class PrimitiveBitRepresentationSample {

    public static void main(String ... args) {
        System.out.println("--- INTEGER ---");
        Integer max = Integer.MAX_VALUE;
        System.out.println(0 + " = " + Integer.toBinaryString(0));
        System.out.println(1 + " = " + Integer.toBinaryString(1));
        System.out.println(2 + " = " + Integer.toBinaryString(2));
        System.out.println(max + " = " + Integer.toBinaryString(max));
        System.out.println(max + 1 + " max + 1 = " + Integer.toBinaryString(max + 1));
        System.out.println(-1 + " = " + Integer.toBinaryString(-1));
        System.out.println(-2 + " = " + Integer.toBinaryString(-2));
        System.out.println(Integer.MIN_VALUE + " = " + Integer.toBinaryString(Integer.MIN_VALUE));
        System.out.println(Integer.MIN_VALUE - 1 + " min - 1 = " + Integer.toBinaryString(Integer.MIN_VALUE - 1));

        System.out.println("--- LONG ---");
        System.out.println(0 + " = " + Long.toBinaryString(0));
        System.out.println(1 + " = " + Long.toBinaryString(1));
        System.out.println(2 + " = " + Long.toBinaryString(2));
        System.out.println(Long.MAX_VALUE + " = " + Long.toBinaryString(Long.MAX_VALUE));
        System.out.println(Long.MAX_VALUE + 1 + " max + 1 = " + Long.toBinaryString(Long.MAX_VALUE + 1));
        System.out.println(-1 + " = " + Long.toBinaryString(-1));
        System.out.println(-2 + " = " + Long.toBinaryString(-2));
        System.out.println(Long.MIN_VALUE + " = " + Long.toBinaryString(Long.MIN_VALUE));
        System.out.println(Long.MIN_VALUE - 1 + " min - 1 = " + Long.toBinaryString(Long.MIN_VALUE - 1));

        System.out.println("--- DOUBLE ---");
        System.out.println("max = " + Double.MAX_VALUE);
        System.out.println("max = " + Double.doubleToLongBits(Double.MAX_VALUE));
        System.out.println("max + 1 = " + (Double.MAX_VALUE + 1));
        System.out.println("max + 1000000 = " + (Double.MAX_VALUE + 1_000_000d));

    }
}
