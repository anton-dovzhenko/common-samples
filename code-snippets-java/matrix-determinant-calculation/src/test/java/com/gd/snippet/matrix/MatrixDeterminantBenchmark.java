package com.gd.snippet.matrix;

import org.openjdk.jmh.annotations.*;

import java.util.concurrent.TimeUnit;

/**
 * Created by anton on 26/12/16.
 */
@Fork(1)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class MatrixDeterminantBenchmark {

    private final static double[][] matrix10x10 = new double[10][10];

    @Benchmark
    public void testLaplaceSingleMatrixDeterminant10x10() {
        new LaplaceSingleMatrixDeterminant().calculate(matrix10x10);
    }

    @Benchmark
    public void testLaplaceForkJoinMatrixDeterminant10x10_Thread_1() {
        new LaplaceForkJoinMatrixDeterminant(1).calculate(matrix10x10);
    }

    @Benchmark
    public void testLaplaceForkJoinMatrixDeterminant10x10_Thread_2() {
        new LaplaceForkJoinMatrixDeterminant(2).calculate(matrix10x10);
    }

    @Benchmark
    public void testLaplaceForkJoinMatrixDeterminant10x10_Thread_3() {
        new LaplaceForkJoinMatrixDeterminant(3).calculate(matrix10x10);
    }

    @Benchmark
    public void testLaplaceForkJoinMatrixDeterminant10x10_Thread_4() {
        new LaplaceForkJoinMatrixDeterminant(4).calculate(matrix10x10);
    }

    @Benchmark
    public void testLaplaceForkJoinMatrixDeterminant10x10_Thread_6() {
        new LaplaceForkJoinMatrixDeterminant(6).calculate(matrix10x10);
    }

    @Benchmark
    public void testLaplaceForkJoinMatrixDeterminant10x10_Thread_8() {
        new LaplaceForkJoinMatrixDeterminant(8).calculate(matrix10x10);
    }

    @Benchmark
    public void testLaplaceForkJoinMatrixDeterminant10x10_Thread_16() {
        new LaplaceForkJoinMatrixDeterminant(16).calculate(matrix10x10);
    }

}
