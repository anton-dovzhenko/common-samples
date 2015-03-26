package com.gammadevs.world;

import junit.framework.Assert;

import java.awt.geom.Point2D;

public class TestUtils {

    public static void assertEquals(Point2D.Double expected, Point2D.Double actual, double delta) {
        Assert.assertNotNull(expected);
        Assert.assertNotNull(actual);
        Assert.assertEquals(expected.getX(), actual.getX(), delta);
        Assert.assertEquals(expected.getY(), actual.getY(), delta);
    }
}
