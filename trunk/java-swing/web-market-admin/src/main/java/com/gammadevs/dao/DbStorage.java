package com.gammadevs.dao;

import com.gammadevs.model.Producer;

import java.util.*;

/**
 * Created by Anton on 9/14/2014.
 * The class is not thread-safe.
 */
public class DbStorage {


    private final static DbStorage instance = new DbStorage();
    private final Map<Long, Producer> producers = new HashMap<Long, Producer>();

    private DbStorage() {
        init();
    }

    public static DbStorage getInstance() {
        return instance;
    }

    private void init() {
        producers.put(1L, new Producer(1L, "Producer1"));
        producers.put(2L, new Producer(2L, "Producer2"));
        producers.put(3L, new Producer(3L, "Producer3"));
        producers.put(4L, new Producer(4L, "Producer4"));
        producers.put(5L, new Producer(5L, "Producer5"));
    }

    public Collection<Producer> getProducers() {
        return producers.values();
    }

    public Object[][] getProducersAsTable() {
        Object[][] result = new Object[producers.size()][2];
        int index = 0;
        for (Producer producer : producers.values()) {
            result[index][0] = producer.getId();
            result[index][1] = producer.getName();
            index++;
        }
        return result;
    }


}
