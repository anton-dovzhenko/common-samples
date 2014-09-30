package com.gammadevs.model;

/**
 * Created by Anton on 9/14/2014.
 */
public class Category implements Comparable<Category> {

    private final Long id;
    private final Long parentId;
    private final String name;

    public Category(Long id, Long parentId, String name) {
        this.id = id;
        this.parentId = parentId;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public Long getParentId() {
        return parentId;
    }

    public String getName() {
        return name;
    }

    @Override
    public int compareTo(Category o) {
        return name.compareTo(o.name);
    }

}
