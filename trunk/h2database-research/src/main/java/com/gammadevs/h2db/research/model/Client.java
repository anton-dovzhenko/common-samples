package com.gammadevs.h2db.research.model;

/**
 * Created by Anton on 12/14/2014.
 */
public class Client {

    private long id;
    private String name;
    private boolean internal;

    public Client() {}
    public Client(long id, String name, boolean internal) {
        this.id = id;
        this.name = name;
        this.internal = internal;
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

    public boolean isInternal() {
        return internal;
    }

    public void setInternal(boolean internal) {
        this.internal = internal;
    }

}
