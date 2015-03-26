package com.gammadevs.gwt.mpa.tabpanel.client.widgets;

import com.google.gwt.user.client.ui.HasOneWidget;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.inject.Inject;
import com.sencha.gxt.widget.core.client.event.SelectEvent;

public class LayoutPresenter {

    public interface Display extends IsWidget {
        HasOneWidget addTab(String name);
        SelectEvent.HasSelectHandlers getBtn1();
        SelectEvent.HasSelectHandlers getBtn2();
        SelectEvent.HasSelectHandlers getBtn3();
    }

    private final Display display;

    @Inject
    public LayoutPresenter(Display display) {
        this.display = display;
    }

    public void start(HasOneWidget container) {
        container.setWidget(display);
    }

}
