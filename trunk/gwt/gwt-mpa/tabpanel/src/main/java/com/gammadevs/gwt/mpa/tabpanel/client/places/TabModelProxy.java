package com.gammadevs.gwt.mpa.tabpanel.client.places;

import com.gammadevs.gwt.mpa.tabpanel.client.TabType;
import com.google.web.bindery.requestfactory.shared.EntityProxy;
import com.google.web.bindery.requestfactory.shared.ProxyFor;

@ProxyFor(TabModel.class)
public interface TabModelProxy extends EntityProxy {

    String getId();
    void setId(String id);
    TabType getTabType();
    void setTabType(TabType tabType);

}
