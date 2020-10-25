package com.gd.snippet.matrix;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by anton on 26/12/16.
 */
public interface MatrixDeterminant {

    double calculate(double[][] matrix);

    default void checkIfSquare(double[][] matrix) {
        int R = matrix.length;
        for (int i = 0; i < R; i++) {
            if (matrix[i].length != R) {
                throw new IllegalArgumentException("Matrix is not square");
            }
        }
    }

    default double get2x2Det(double[][] m, int i, int j) {
        return m[i][j] * m[i + 1][j + 1] - m[i][j + 1] * m[i + 1][j];
    }

    default double get2x2Det(double[][] matrix, int row, List<Integer> columnExcluded) {
        List<Integer> columns = new ArrayList<>();
        for (int j = 0; j < matrix.length; j++) {
            if (!columnExcluded.contains(j)) {
                columns.add(j);
            }
        }
        Collections.sort(columns);
        return matrix[row][columns.get(0)] * matrix[row + 1][columns.get(1)]
                - matrix[row][columns.get(1)] * matrix[row + 1][columns.get(0)];
    }

}
