package com.gammadevs.gwt.mpa.tabpanel.client;

import com.gammadevs.gwt.mpa.tabpanel.client.widgets.LayoutPresenter;
import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;

@GinModules(MpaTabPanelGinModule.class)
public interface MpaTabPanelGinjector extends Ginjector {

    LayoutPresenter getLayoutPresenter();

}
