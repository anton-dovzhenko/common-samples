package com.gd.snippet.pi;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.concurrent.TimeUnit;

/**
 * Created by anton on 26/12/16.
 * No difference after warm-up observed.
 */
@Fork(1)
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class PiCalculatorMicroBenchmark {

    private static final SinglePiCalculator calculator = new SinglePiCalculator();

    @Benchmark
    public void testGetSequenceValue_100(Blackhole blackhole) {
        blackhole.consume(calculator.getSequenceValue(100));
    }

    @Benchmark
    public void testGetSequenceValue2_100(Blackhole blackhole) {
        blackhole.consume(calculator.getSequenceValue2(100));
    }

    @Benchmark
    public void testGetSequenceValue_1_000_000(Blackhole blackhole) {
        blackhole.consume(calculator.getSequenceValue(1_000_000));
    }

    @Benchmark
    public void testGetSequenceValue2_1_000_000(Blackhole blackhole) {
        blackhole.consume(calculator.getSequenceValue2(1_000_000));
    }

}
