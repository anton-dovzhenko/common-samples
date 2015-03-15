package com.gammadevs.gwt.gxtwidgets.client.examples;

import com.gammadevs.gwt.gxtwidgets.client.widgets.FrozenGrid;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.widget.core.client.container.HorizontalLayoutContainer;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FrozenGridExample implements IsWidget {

    interface FrozenGridExampleUiBinder extends UiBinder<HorizontalLayoutContainer, FrozenGridExample> {}
    private static FrozenGridExampleUiBinder ourUiBinder = GWT.create(FrozenGridExampleUiBinder.class);

    private final HorizontalLayoutContainer rootElement;

    @UiField(provided = true)
    FrozenGrid<FrozenGridModel> grid;

    public FrozenGridExample() {
        FrozenGridModelProperties props = GWT.create(FrozenGridModelProperties.class);

        List<ColumnConfig<FrozenGridModel, ?>> lockedColumns = new ArrayList<>();
        List<ColumnConfig<FrozenGridModel, ?>> columns = new ArrayList<>();

        lockedColumns.add(new ColumnConfig<>(props.id(), 100, "Id"));
        lockedColumns.add(new ColumnConfig<>(props.time(), 250, "Time"));

        columns.add(new ColumnConfig<>(props.metric1(), 120, "Metric 1"));
        columns.add(new ColumnConfig<>(props.metric2(), 120, "Metric 2"));
        columns.add(new ColumnConfig<>(props.metric3(), 120, "Metric 3"));
        columns.add(new ColumnConfig<>(props.metric4(), 120, "Metric 4"));
        columns.add(new ColumnConfig<>(props.metric5(), 120, "Metric 5"));
        columns.add(new ColumnConfig<>(props.metric6(), 120, "Metric 6"));
        columns.add(new ColumnConfig<>(props.metric7(), 120, "Metric 7"));
        columns.add(new ColumnConfig<>(props.metric8(), 120, "Metric 8"));
        columns.add(new ColumnConfig<>(props.metric9(), 120, "Metric 9"));
        columns.add(new ColumnConfig<>(props.metric10(), 120, "Metric 10"));
        columns.add(new ColumnConfig<>(props.metric11(), 120, "Metric 11"));
        columns.add(new ColumnConfig<>(props.metric12(), 120, "Metric 12"));
        columns.add(new ColumnConfig<>(props.metric13(), 120, "Metric 13"));

        grid = new FrozenGrid<>(props.key(), lockedColumns, columns);

        rootElement = ourUiBinder.createAndBindUi(this);

        List<FrozenGridModel> items = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            items.add(new FrozenGridModel(10 * i
            , new Date(i * 533)
            , i * Math.random()
            , i
            , i
            , i
            , i
            , i
            , i
            , i
            , i
            , i
            , i
            , i
            , i));
        }
        grid.setModel(items);
    }

    @Override
    public Widget asWidget() {
        return rootElement;
    }


}