package com.gammadevs.cip;

import static org.junit.Assert.*;

public class LatchExampleTest {

    @org.junit.Test
    public void testRunTasks() throws Exception {
        ThreadJoinEmulationLatchExample example = new ThreadJoinEmulationLatchExample();
        example.runTasks(10, new Runnable() {
            @Override
            public void run() {
                System.out.println("Task completed by thread: " + Thread.currentThread().getName() + " time:" + System.currentTimeMillis());
            }
        });
    }
}