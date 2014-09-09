package com.gammadevs.cip;

public class ThreadJoinEmulationLatchExampleTest {

    @org.junit.Test
    public void testRunTasks() throws Exception {
        ThreadJoinEmulationLatchExample example = new ThreadJoinEmulationLatchExample();
        example.runTasks(10, new Runnable() {
            @Override
            public void run() {
                System.out.println("Task completed by thread: " + Thread.currentThread().getName()
                        + " time:" + System.currentTimeMillis());
            }
        });
    }
}