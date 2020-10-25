package com.gd.snippet.matrix;

import org.junit.Test;

import static com.gd.snippet.matrix.TestConstants.error;
import static org.junit.Assert.assertEquals;

/**
 * Created by anton on 26/12/16.
 */
public class LaplaceForkJoinMatrixDeterminantTest {

    @Test
    public void calculate1ThreadTest() {
        calculateNThreadsTest(1);
    }

    @Test
    public void calculate2ThreadTest() {
        calculateNThreadsTest(2);
    }

    @Test
    public void calculate4ThreadTest() {
        calculateNThreadsTest(4);
    }

    @Test
    public void calculateBigTest1ThreadTest() {
        calculateBigTestNThreadsTest(1);
    }

    @Test
    public void calculateBigTest2ThreadTest() {
        calculateBigTestNThreadsTest(2);
    }

    @Test
    public void calculateBigTest4ThreadTest() {
        calculateBigTestNThreadsTest(4);
    }

    private void calculateNThreadsTest(int N) {
        final LaplaceForkJoinMatrixDeterminant det = new LaplaceForkJoinMatrixDeterminant(N);
        assertEquals(-2d, det.calculate(new double[][] {{1, 2}, {3, 4}}), error);
        assertEquals(0d, det.calculate(new double[][] {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}}), error);
        assertEquals(-306d, det.calculate(new double[][] {{6, 1, 1}, {4, -2, 5}, {2, 8, 7}}), error);
    }

    private void calculateBigTestNThreadsTest(int N) {
        final LaplaceForkJoinMatrixDeterminant det = new LaplaceForkJoinMatrixDeterminant(N);
        double[][] matrix = new double[10][10];
        assertEquals(0d, det.calculate(matrix), error);

        matrix = new double[10][10];
        for (int i = 0; i < 100; i++) {
            matrix[i / 10][ i % 10] = 1 + i;
        }
        assertEquals(0d, det.calculate(matrix), error);
    }

}