package com.gd.snippet.fastmoney;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

/**
 * Created by anton on 3/1/17.
 */
@Fork(1)
@Warmup(iterations = 5)
@Measurement(iterations = 5)
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class LongMoneyBenchmark {

    @Benchmark
    public void testLongMoneyAddSamePrecision1(Blackhole blackhole) {
        LongMoney result =  new LongMoney(100, 2).add(new LongMoney(0, 2), 2);
        blackhole.consume(result);
    }

    @Benchmark
    public void testBigDecimalAddSamePrecision1(Blackhole blackhole) {
        BigDecimal result =  new BigDecimal(100).setScale(2, BigDecimal.ROUND_DOWN).add(new BigDecimal(0).setScale(2, BigDecimal.ROUND_DOWN));
        blackhole.consume(result);
    }

    @Benchmark
    public void testLongMoneyAddSamePrecision2(Blackhole blackhole) {
        LongMoney result =  new LongMoney(50, 2).add(new LongMoney(50, 2), 2);
        blackhole.consume(result);
    }

    @Benchmark
    public void testBigDecimalAddSamePrecision2(Blackhole blackhole) {
        BigDecimal result =  new BigDecimal(50).setScale(2, BigDecimal.ROUND_DOWN).add(new BigDecimal(50).setScale(2, BigDecimal.ROUND_DOWN));
        blackhole.consume(result);
    }

    @Benchmark
    public void testLongMoneyAddSamePrecision3(Blackhole blackhole) {
        LongMoney result =  new LongMoney(25, 2).add(new LongMoney(75, 2), 2);
        blackhole.consume(result);
    }

    @Benchmark
    public void testBigDecimalAddSamePrecision3(Blackhole blackhole) {
        BigDecimal result =  new BigDecimal(25).setScale(2, BigDecimal.ROUND_DOWN).add(new BigDecimal(75).setScale(2, BigDecimal.ROUND_DOWN));
        blackhole.consume(result);
    }

    @Benchmark
    public void testLongMoneyAddDifferentPrecisions1(Blackhole blackhole) {
        LongMoney result =  new LongMoney(1000, 3).add(new LongMoney(75, 10), 2);
        blackhole.consume(result);
    }

    @Benchmark
    public void testBigDecimalAddDifferentPrecisions1(Blackhole blackhole) {
        BigDecimal result =  new BigDecimal(1000).setScale(3, BigDecimal.ROUND_DOWN).add(new BigDecimal(75).setScale(10, BigDecimal.ROUND_DOWN))
                .setScale(2, BigDecimal.ROUND_DOWN);
        blackhole.consume(result);
    }

    @Benchmark
    public void testLongMoneyAddDifferentPrecisions2(Blackhole blackhole) {
        LongMoney result =  new LongMoney(500, 3).add(new LongMoney(50, 2), 2);
        blackhole.consume(result);
    }

    @Benchmark
    public void testBigDecimalAddDifferentPrecisions2(Blackhole blackhole) {
        BigDecimal result =  new BigDecimal(500).setScale(3, BigDecimal.ROUND_DOWN).add(new BigDecimal(50).setScale(2, BigDecimal.ROUND_DOWN))
                .setScale(2, BigDecimal.ROUND_DOWN);
        blackhole.consume(result);
    }

    @Benchmark
    public void testLongMoneyAddDifferentPrecisions3(Blackhole blackhole) {
        LongMoney result =  new LongMoney(250, 3).add(new LongMoney(7500, 4), 2);
        blackhole.consume(result);
    }

    @Benchmark
    public void testBigDecimalAddDifferentPrecisions3(Blackhole blackhole) {
        BigDecimal result =  new BigDecimal(250).setScale(3, BigDecimal.ROUND_DOWN).add(new BigDecimal(7500).setScale(4, BigDecimal.ROUND_DOWN))
                .setScale(2, BigDecimal.ROUND_DOWN);
        blackhole.consume(result);
    }
}
