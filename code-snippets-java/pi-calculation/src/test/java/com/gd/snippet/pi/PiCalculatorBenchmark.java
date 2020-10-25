package com.gd.snippet.pi;

import org.openjdk.jmh.annotations.*;

import java.util.concurrent.TimeUnit;

/**
 * Created by anton on 26/12/16.
 */
@Fork(1)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class PiCalculatorBenchmark {

    @Benchmark
    public void testSinglePiCalculator1_000_000N() {
        new SinglePiCalculator().calculate(1_000_000);
    }

    @Benchmark
    public void testStreamPiCalculator1_000_000N() {
        new StreamPiCalculator().calculate(1_000_000);
    }

    @Benchmark
    public void testThreadPiCalculator_1_Thread1_000_000N() {
        new ThreadPiCalculator(1).calculate(1_000_000);
    }

    @Benchmark
    public void testThreadPiCalculator_2_Thread1_000_000N() {
        new ThreadPiCalculator(2).calculate(1_000_000);
    }

    @Benchmark
    public void testThreadPiCalculator_4_Thread1_000_000N() {
        new ThreadPiCalculator(4).calculate(1_000_000);
    }

    @Benchmark
    public void testThreadPiCalculator_6_Thread1_000_000N() {
        new ThreadPiCalculator(6).calculate(1_000_000);
    }

    @Benchmark
    public void testThreadPiCalculator_8_Thread1_000_000N() {
        new ThreadPiCalculator(8).calculate(1_000_000);
    }

    @Benchmark
    public void testThreadPiCalculator_16_Thread1_000_000N() {
        new ThreadPiCalculator(16).calculate(1_000_000);
    }

    @Benchmark
    public void testSinglePiCalculator10_000_000N() {
        new SinglePiCalculator().calculate(10_000_000);
    }

    @Benchmark
    public void testStreamPiCalculator10_000_000N() {
        new StreamPiCalculator().calculate(10_000_000);
    }

    @Benchmark
    public void testThreadPiCalculator_1_Thread10_000_000N() {
        new ThreadPiCalculator(1).calculate(10_000_000);
    }

    @Benchmark
    public void testThreadPiCalculator_2_Thread10_000_000N() {
        new ThreadPiCalculator(2).calculate(10_000_000);
    }

    @Benchmark
    public void testThreadPiCalculator_4_Thread10_000_000N() {
        new ThreadPiCalculator(4).calculate(10_000_000);
    }

    @Benchmark
    public void testThreadPiCalculator_6_Thread10_000_000N() {
        new ThreadPiCalculator(6).calculate(10_000_000);
    }

    @Benchmark
    public void testThreadPiCalculator_8_Thread10_000_000N() {
        new ThreadPiCalculator(8).calculate(10_000_000);
    }

    @Benchmark
    public void testThreadPiCalculator_16_Thread10_000_000N() {
        new ThreadPiCalculator(16).calculate(10_000_000);
    }

}
