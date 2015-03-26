package com.gammadevs.gwt.mpa.tabpanel.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootLayoutPanel;


public class MpaTabPanelEntryPoint implements EntryPoint {

    @Override
    public void onModuleLoad() {
        new MpaTabPanelController().go(RootLayoutPanel.get());
    }

}
