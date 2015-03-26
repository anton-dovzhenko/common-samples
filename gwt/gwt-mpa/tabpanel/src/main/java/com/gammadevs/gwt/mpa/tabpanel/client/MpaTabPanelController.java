package com.gammadevs.gwt.mpa.tabpanel.client;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.SimpleLayoutPanel;
import com.sencha.gxt.widget.core.client.container.SimpleContainer;

/**
 * Created by Anton on 3/23/2015.
 */
public class MpaTabPanelController {

    private final MpaTabPanelGinjector injector = GWT.create(MpaTabPanelGinjector.class);

    MpaTabPanelController() {

    }

    void go(RootLayoutPanel root) {
        SimpleContainer panel = new SimpleContainer();
        root.add(panel);
        injector.getLayoutPresenter().start(panel);
    }
}
