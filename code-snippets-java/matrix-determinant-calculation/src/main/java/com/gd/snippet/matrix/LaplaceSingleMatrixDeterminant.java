package com.gd.snippet.matrix;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.lang.Math.pow;

/**
 * Created by anton on 26/12/16.
 */
public class LaplaceSingleMatrixDeterminant implements MatrixDeterminant {

    public double calculate(double[][] matrix) {
        checkIfSquare(matrix);
        return det(matrix, 0, new ArrayList<>());
    }

    double det(double[][] matrix, int row, List<Integer> columnExcluded) {
        if (matrix.length == row + 2) {
            return get2x2Det(matrix, row, columnExcluded);
        } else {
            int index = 0;
            double sum = 0;
            for (int j = 0; j < matrix.length; j++) {
                if(!columnExcluded.contains(j)) {
                    List<Integer> nextColumnExcluded = new ArrayList<>(columnExcluded);
                    nextColumnExcluded.add(j);
                    sum += pow(-1, index) * matrix[row][j] * det(matrix, row + 1, nextColumnExcluded);
                    index++;
                }
            }
            return sum;
        }
    }



}
