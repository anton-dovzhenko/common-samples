package com.gammadevs.gwt.mpa.tabpanel.client.places;

import com.gammadevs.gwt.mpa.tabpanel.client.TabType;

/**
 * Created by Anton on 3/23/2015.
 */
public class TabModel {

    private final String id;
    private final TabType tabType;

    public TabModel(String id, TabType tabType) {
        this.id = id;
        this.tabType = tabType;
    }

    public String getId() {
        return id;
    }

    public TabType getTabType() {
        return tabType;
    }
}
