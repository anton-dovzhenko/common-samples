package com.gammadevs.gwt.mpa.tabpanel.client.widgets;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HasOneWidget;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.widget.core.client.TabItemConfig;
import com.sencha.gxt.widget.core.client.TabPanel;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
import com.sencha.gxt.widget.core.client.container.SimpleContainer;
import com.sencha.gxt.widget.core.client.event.SelectEvent;

public class LayoutView implements LayoutPresenter.Display {

    interface LayoutViewUiBinder extends UiBinder<BorderLayoutContainer, LayoutView> {}
    private static LayoutViewUiBinder ourUiBinder = GWT.create(LayoutViewUiBinder.class);

    private final BorderLayoutContainer rootElement;

    @UiField
    TabPanel tabPanel;
    @UiField
    TextButton btn1;
    @UiField
    TextButton btn2;
    @UiField
    TextButton btn3;

    public LayoutView() {
        rootElement = ourUiBinder.createAndBindUi(this);
    }

    @Override
    public Widget asWidget() {
        return rootElement;
    }

    @Override
    public HasOneWidget addTab(String name) {
        SimpleContainer container = new SimpleContainer();
        tabPanel.add(container, new TabItemConfig(name));
        return container;
    }

    @Override
    public SelectEvent.HasSelectHandlers getBtn1() {
        return btn1;
    }

    @Override
    public SelectEvent.HasSelectHandlers getBtn2() {
        return btn2;
    }

    @Override
    public SelectEvent.HasSelectHandlers getBtn3() {
        return btn3;
    }

}