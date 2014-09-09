package com.gammadevs.cip;

import junit.framework.Assert;
import org.junit.Test;

import java.util.HashSet;

import static org.junit.Assert.*;

public class BoundedHashSetSemaphoreExampleTest {

    @Test
    public void testAddAndRemove() throws Exception {
        final BoundedHashSetSemaphoreExample<Integer> set = new BoundedHashSetSemaphoreExample<Integer>(5);
        int index = 1;
        set.add(index++);
        set.add(index++);
        set.add(index++);
        set.add(index++);
        set.add(index);

        final int elementToAdd = index + 1;
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    set.add(elementToAdd);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        set.remove(index);
        thread.join();
    }

}