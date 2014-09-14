package com.gammadevs.model;

/**
 * Created by Anton on 9/14/2014.
 */
public class Producer {

    private long id;
    private String name;

    public Producer(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
