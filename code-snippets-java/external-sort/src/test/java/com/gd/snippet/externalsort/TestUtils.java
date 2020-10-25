package com.gd.snippet.externalsort;

import org.junit.Assert;

import java.io.*;

/**
 * Created by anton on 18/4/17.
 */
public final class TestUtils {

    private TestUtils() {}

    public static void assertIntFileIsSortedAsc(File sorted) throws IOException {
        try (DataInputStream dis = new DataInputStream(new BufferedInputStream(new FileInputStream(sorted)))) {
            int prevValue = Integer.MIN_VALUE;
            while (dis.available() > 0) {
                int value = dis.readInt();
                Assert.assertFalse(value < prevValue);
                prevValue = value;
            }
        }
    }

}
