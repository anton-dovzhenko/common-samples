package com.gammadevs.cip;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Semaphore;

/**
 * Created by Anton on 9/6/2014.
 */
public class BoundedHashSetSemaphoreExample<T> {

    private final Set<T> set;
    private final Semaphore semaphore;

    public BoundedHashSetSemaphoreExample(int bound) {
        this.set = new HashSet<T>();
        this.semaphore = new Semaphore(bound);
    }

    public boolean add(T value) throws InterruptedException {
        semaphore.acquire();
        boolean added = false;
        try {
            added = set.add(value);
            return added;
        } finally {
            if (!added) {
                semaphore.release();
            }
        }
    }

    public boolean remove(T value) {
        boolean removed = set.remove(value);
        if (removed) {
            semaphore.release();
        }
        return removed;
    }
}
