package com.gammadevs.gwt.gxtwidgets.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootLayoutPanel;

public class GxtWidgetsEntryPoint implements EntryPoint {

    public void onModuleLoad() {
        GxtWidgetsMainLayout layout = new GxtWidgetsMainLayout();
        RootLayoutPanel.get().add(layout);

    }

}
