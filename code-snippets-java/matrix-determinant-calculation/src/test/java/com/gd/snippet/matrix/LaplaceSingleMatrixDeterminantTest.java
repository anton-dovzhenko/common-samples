package com.gd.snippet.matrix;

import org.junit.Test;

import static com.gd.snippet.matrix.TestConstants.error;
import static org.junit.Assert.*;

/**
 * Created by anton on 26/12/16.
 */
public class LaplaceSingleMatrixDeterminantTest {

    @Test
    public void checkIfSquareSuccessTest() {
        final LaplaceSingleMatrixDeterminant det = new LaplaceSingleMatrixDeterminant();
        det.checkIfSquare(new double[][] {{1}});
        det.checkIfSquare(new double[][] {{1, 2}, {3, 4}});
        det.checkIfSquare(new double[][] {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}});
    }

    @Test(expected = IllegalArgumentException.class)
    public void checkIfSquareFail1Test() {
        final LaplaceSingleMatrixDeterminant det = new LaplaceSingleMatrixDeterminant();
        det.checkIfSquare(new double[][] {{1, 2, 5}, {3, 4}});
    }
    @Test(expected = IllegalArgumentException.class)
    public void checkIfSquareFail2Test() {
        final LaplaceSingleMatrixDeterminant det = new LaplaceSingleMatrixDeterminant();
        det.checkIfSquare(new double[][] {{1, 2}, {3, 4, 5}});
    }

    @Test
    public void get2x2DetTest() {
        final LaplaceSingleMatrixDeterminant det = new LaplaceSingleMatrixDeterminant();
        assertEquals(-2d, det.get2x2Det(new double[][] {{1, 2}, {3, 4}}, 0, 0), error);
        assertEquals(-3d, det.get2x2Det(new double[][] {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}}, 0, 0), error);
        assertEquals(-3d, det.get2x2Det(new double[][] {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}}, 1, 1), error);
    }

    @Test
    public void calculateTest() {
        final LaplaceSingleMatrixDeterminant det = new LaplaceSingleMatrixDeterminant();
        assertEquals(-2d, det.calculate(new double[][] {{1, 2}, {3, 4}}), error);
        assertEquals(0d, det.calculate(new double[][] {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}}), error);
        assertEquals(-306d, det.calculate(new double[][] {{6, 1, 1}, {4, -2, 5}, {2, 8, 7}}), error);
    }

    @Test
    public void calculateBigTest() {
        final LaplaceSingleMatrixDeterminant det = new LaplaceSingleMatrixDeterminant();
        double[][] matrix = new double[10][10];
        assertEquals(0d, det.calculate(matrix), error);

        matrix = new double[10][10];
        for (int i = 0; i < 100; i++) {
            matrix[i / 10][ i % 10] = 1 + i;
        }
        assertEquals(0d, det.calculate(matrix), error);
    }

}