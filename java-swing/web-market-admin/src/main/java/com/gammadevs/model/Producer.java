package com.gammadevs.model;

/**
 * Created by Anton on 9/14/2014.
 */
public class Producer {

    private final Long id;
    private final String name;

    public Producer(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

}
