package com.gammadevs.inverview.samples.concurrency;

/**
 * Created by Anton on 10/24/2014.
 */
public class ProducerConsumerExample {

    public static void main(String[] args) throws InterruptedException {
        Box box = new Box(5);
        Thread consumer = new Thread(new Consumer(box));
        Thread producer = new Thread(new Producer(box));
        consumer.start();
        producer.start();
        Thread.sleep(5000);
        consumer.interrupt();
        producer.interrupt();
    }

    public static class Producer implements Runnable {

        private final Box box;

        public Producer(Box box) {
            this.box = box;
        }

        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    for (int i = 0; i < 10; i++) {
                        box.add();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    System.out.println("Producer thread was interrupted.");
                    break;
                }
            }
        }
    }

    public static class Consumer implements Runnable {

        private final Box box;

        public Consumer(Box box) {
            this.box = box;
        }

        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    Thread.sleep(500);
                    for (int i = 0; i < 10; i++) {
                        box.get();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    System.out.println("Consumer thread was interrupted.");
                    break;
                }
            }
        }
    }

    public static class Box {

        private final int size;
        private volatile int filled;

        public Box(int size) {
            this.size = size;
        }

        public synchronized void add() throws InterruptedException {
            while (filled == size) {
                wait();
            }
            filled++;
            System.out.println("Add. Box filled = " + filled);
            notifyAll();
        }

        public synchronized void get() throws InterruptedException {
            while (filled == 0) {
                wait();
            }
            filled--;
            System.out.println("Get. Box filled = " + filled);
            notifyAll();
        }

    }

}
