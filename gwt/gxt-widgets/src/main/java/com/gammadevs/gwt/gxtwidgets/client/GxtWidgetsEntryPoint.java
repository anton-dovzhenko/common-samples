package com.gammadevs.gwt.gxtwidgets.client;

import com.gammadevs.gwt.gxtwidgets.client.examples.FrozenGridExample;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.*;

/**
 * Created by Anton on 3/9/2015.
 */
public class GxtWidgetsEntryPoint implements EntryPoint {

    public void onModuleLoad() {
        GxtWidgetsMainLayout layout = new GxtWidgetsMainLayout();
        RootLayoutPanel.get().add(layout);
    }

}
