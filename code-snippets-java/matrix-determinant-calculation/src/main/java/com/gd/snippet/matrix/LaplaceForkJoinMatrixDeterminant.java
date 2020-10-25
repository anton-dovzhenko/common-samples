package com.gd.snippet.matrix;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

import static java.lang.Math.pow;

/**
 * Created by anton on 26/12/16.
 */
public class LaplaceForkJoinMatrixDeterminant implements MatrixDeterminant {

    private final int threadCount;

    public LaplaceForkJoinMatrixDeterminant(int threadCount) {
        this.threadCount = threadCount;
    }

    @Override
    public double calculate(double[][] matrix) {
        checkIfSquare(matrix);
        final ForkJoinPool forkJoinPool = new ForkJoinPool(threadCount);
        final DeterminantTask task = new DeterminantTask(matrix, 0, new ArrayList<>(), 1);
        double result = forkJoinPool.invoke(task);
        forkJoinPool.shutdown();
        return result;
    }

    private class DeterminantTask extends RecursiveTask<Double> {

        private final double[][] matrix;
        private final int row;
        private final List<Integer> columnExcluded;
        private final double multiplier;

        private DeterminantTask(double[][] matrix, int row, List<Integer> columnExcluded, double multiplier) {
            this.matrix = matrix;
            this.row = row;
            this.columnExcluded = columnExcluded;
            this.multiplier = multiplier;
        }

        @Override
        protected Double compute() {
            if (matrix.length == row + 2) {
                return multiplier * get2x2Det(matrix, row, columnExcluded);
            } else {
                int index = 0;
                double sum = 0;
                List<DeterminantTask> tasks = new ArrayList<>();
                for (int j = 0; j < matrix.length; j++) {
                    if(!columnExcluded.contains(j)) {
                        List<Integer> nextColumnExcluded = new ArrayList<>(columnExcluded);
                        nextColumnExcluded.add(j);
                        final DeterminantTask next = new DeterminantTask(matrix, row + 1, nextColumnExcluded, pow(-1, index) * matrix[row][j]);
                        next.fork();
                        tasks.add(next);
                        index++;
                    }
                }
                for (DeterminantTask task : tasks) {
                    sum += task.join();
                }

                return multiplier * sum;
            }
        }
    }

}
