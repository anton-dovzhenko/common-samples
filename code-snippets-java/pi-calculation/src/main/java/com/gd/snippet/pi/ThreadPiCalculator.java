package com.gd.snippet.pi;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by anton on 26/12/16.
 */
public class ThreadPiCalculator implements PiCalculator {

    private final int threadCount;

    public ThreadPiCalculator(int threadCount) {
        this.threadCount = threadCount;
    }

    @Override
    public double calculate(long N) {
        checkN(N);
        final AtomicLong counter = new AtomicLong();
        List<PiThread> threads = new ArrayList<>(threadCount);
        for (int i = 0; i < threadCount; i++) {
            PiThread thread = new PiThread(counter, N);
            threads.add(thread);
            thread.start();
        }
        for (PiThread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
        }

        double pi = 0;
        for (PiThread thread : threads) {
            pi += thread.getSum();
        }
        return 4 * pi;
    }

    class PiThread extends Thread {

        private final AtomicLong counter;
        private final long N;
        private double sum = 0;

        private PiThread(AtomicLong counter, long n) {
            this.counter = counter;
            N = n;
        }

        @Override
        public void run() {
            long i;
            while (isValidCounter(i = counter.getAndIncrement())) {
                sum += getSequenceValue(i);
            }
        }

        private boolean isValidCounter(long value) {
            return value >= 0 && value <= N;
        }

        private double getSum() {
            return sum;
        }
    }

}
