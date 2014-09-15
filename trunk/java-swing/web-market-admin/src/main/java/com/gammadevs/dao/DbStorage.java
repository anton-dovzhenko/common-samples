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
    private Long uniqueIndex = 0L;

    private DbStorage() {
        init();
    }

    public static DbStorage getInstance() {
        return instance;
    }

    private void init() {
        producers.put(++uniqueIndex, new Producer(uniqueIndex, "Producer1"));
        producers.put(++uniqueIndex, new Producer(uniqueIndex, "Producer2"));
        producers.put(++uniqueIndex, new Producer(uniqueIndex, "Producer3"));
        producers.put(++uniqueIndex, new Producer(uniqueIndex, "Producer4"));
        producers.put(++uniqueIndex, new Producer(uniqueIndex, "Producer5"));
    }

    public Producer saveOrUpdate(Producer producer) {
        Producer item = producer.getId() != null
                ? producer : new Producer(++uniqueIndex, producer.getName());
        producers.put(item.getId(), item);
        return item;
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
