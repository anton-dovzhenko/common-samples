package com.gammadevs.gwt.mpa.tabpanel.client;

import com.gammadevs.gwt.mpa.tabpanel.client.widgets.LayoutPresenter;
import com.gammadevs.gwt.mpa.tabpanel.client.widgets.LayoutView;
import com.google.gwt.inject.client.AbstractGinModule;
import com.google.inject.Singleton;

public class MpaTabPanelGinModule extends AbstractGinModule {

    @Override
    protected void configure() {
        bind(LayoutPresenter.class).in(Singleton.class);
        bind(LayoutPresenter.Display.class).to(LayoutView.class);
    }

}
