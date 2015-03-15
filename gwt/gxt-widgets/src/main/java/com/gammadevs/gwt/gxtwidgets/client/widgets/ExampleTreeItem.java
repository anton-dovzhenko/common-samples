package com.gammadevs.gwt.gxtwidgets.client.widgets;

import com.gammadevs.gwt.gxtwidgets.client.ExampleType;

public class ExampleTreeItem {

    private final String name;
    private final ExampleType type;

    public ExampleTreeItem(String name, ExampleType type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public ExampleType getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ExampleTreeItem that = (ExampleTreeItem) o;

        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (type != that.type) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }
}
