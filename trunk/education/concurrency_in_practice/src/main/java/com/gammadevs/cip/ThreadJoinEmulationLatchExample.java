package com.gammadevs.cip;

import java.util.concurrent.CountDownLatch;

/**
 * Created by Anton on 9/6/2014.
 */
public class ThreadJoinEmulationLatchExample {

    public long runTasks(int numberOfThreads, final Runnable task) throws InterruptedException {
        final CountDownLatch startLatch = new CountDownLatch(1);
        final CountDownLatch endLatch = new CountDownLatch(numberOfThreads);
        for (int i = 0; i < numberOfThreads; i++) {
            Thread.sleep(500);
            Thread thread = new Thread() {
                @Override
                public void run() {
                    try {
                        startLatch.await();
                        try {
                            task.run();
                        } finally {
                            endLatch.countDown();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };
            thread.start();
        }
        long start = System.nanoTime();
        startLatch.countDown();
        endLatch.await();
        long end = System.nanoTime();
        return end - start;

    }
}
