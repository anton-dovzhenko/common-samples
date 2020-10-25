package com.gd.snippet.externalsort;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.*;

/**
 * Created by anton on 17/4/17.
 */
public class ExternalIntSort1MTest {

    @BeforeClass
    public static void createFile() throws IOException {
        try (DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(new FileOutputStream("1Mint.dat")))) {
            for (int i = 0; i < 1_000_000; i++) {
                dos.writeInt(1_000_000 + i);
            }
        }
    }

    @Test
    public void sort100KChunkTest() throws IOException {
        File sorted = new ExternalIntSort(new File("1Mint.dat"), 100_000).sort();
        TestUtils.assertIntFileIsSortedAsc(sorted);
    }

    @Test
    public void sort25KChunkTest() throws IOException {
        File sorted = new ExternalIntSort(new File("1Mint.dat"), 25_000).sort();
        TestUtils.assertIntFileIsSortedAsc(sorted);
    }

    @AfterClass
    public static void deleteFile() {
        Assert.assertTrue(new File("1Mint.dat").delete());
    }

}