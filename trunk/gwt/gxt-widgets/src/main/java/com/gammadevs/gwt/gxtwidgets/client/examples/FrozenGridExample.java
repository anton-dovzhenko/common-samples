package com.gammadevs.gwt.gxtwidgets.client.examples;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.core.client.Style;
import com.sencha.gxt.core.client.dom.XDOM;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.core.client.util.Size;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.widget.core.client.container.HorizontalLayoutContainer;
import com.sencha.gxt.widget.core.client.event.BodyScrollEvent;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.grid.GridSelectionModel;
import com.sencha.gxt.widget.core.client.selection.SelectionChangedEvent;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Anton on 3/9/2015.
 */
public class FrozenGridExample implements IsWidget {

    private static class FrozenGridSelectionModel<M> extends GridSelectionModel<M> {
        @Override
        public void doSelect(List<M> models, boolean keepExisting, boolean suppressEvent) {
            super.doSelect(models, keepExisting, suppressEvent);
        }

    }

    interface FrozenGridExampleUiBinder extends UiBinder<HorizontalLayoutContainer, FrozenGridExample> {}
    private static FrozenGridExampleUiBinder ourUiBinder = GWT.create(FrozenGridExampleUiBinder.class);

    private final ListStore<FrozenGridModel> store;
    private final FrozenGridSelectionModel<FrozenGridModel> lockedGridSelectionModel;
    private final FrozenGridSelectionModel<FrozenGridModel> gridSelectionModel;
    private final HorizontalLayoutContainer rootElement;

    @UiField(provided = true)
    HorizontalLayoutContainer.HorizontalLayoutData lockedLayoutData;
    @UiField(provided = true)
    Grid<FrozenGridModel> lockedGrid;
    @UiField(provided = true)
    Grid<FrozenGridModel> grid;

    public FrozenGridExample() {
        FrozenGridModelProperties props = GWT.create(FrozenGridModelProperties.class);
        store = new ListStore<>(props.key());
        lockedGridSelectionModel = new FrozenGridSelectionModel<>();
        gridSelectionModel = new FrozenGridSelectionModel<>();

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

        ColumnModel<FrozenGridModel> lockCm = new ColumnModel<>(lockedColumns);
        ColumnModel<FrozenGridModel> cm = new ColumnModel<>(columns);

        lockedGrid = new Grid<FrozenGridModel>(store, lockCm) {
            @Override
            protected Size adjustSize(Size size) {
                return new Size(size.getWidth() + XDOM.getScrollBarWidth() - 1, size.getHeight());
            }
        };
        lockedGrid.setSelectionModel(lockedGridSelectionModel);

        grid = new Grid<>(store, cm);
        grid.setSelectionModel(gridSelectionModel);

        lockedLayoutData = new HorizontalLayoutContainer.HorizontalLayoutData(getLockedWidth(lockCm), 1);
        lockedLayoutData.setMargins(new Margins(1, 0, XDOM.getScrollBarWidth(), 0));

        lockedGrid.getView().setAdjustForHScroll(false);

        grid.setBorders(true);

        synchronizeScrolling();
        synchronizeSelection();

        rootElement = ourUiBinder.createAndBindUi(this);

        List<FrozenGridModel> items = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            items.add(new FrozenGridModel(10 * i
            , new Date(i * 533)
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
            , i
            , i));
        }
        setModel(items);
    }

    @Override
    public Widget asWidget() {
        return rootElement;
    }

    private double getLockedWidth(ColumnModel<FrozenGridModel> columnModel) {
        double width = 0;
        for (ColumnConfig<FrozenGridModel, ?> column : columnModel.getColumns()) {
            width += column.getWidth();
        }
        return width;

    }

    private void synchronizeScrolling() {
        lockedGrid.addBodyScrollHandler(new BodyScrollEvent.BodyScrollHandler() {
            @Override
            public void onBodyScroll(BodyScrollEvent event) {
                grid.getView().getScroller().scrollTo(Style.ScrollDirection.TOP, event.getScrollTop());
            }
        });
        grid.addBodyScrollHandler(new BodyScrollEvent.BodyScrollHandler() {
            @Override
            public void onBodyScroll(BodyScrollEvent event) {
                lockedGrid.getView().getScroller().scrollTo(Style.ScrollDirection.TOP, event.getScrollTop());
            }
        });
    }

    private void synchronizeSelection() {
        lockedGrid.getSelectionModel().addSelectionHandler(new SelectionHandler() {
            @Override
            public void onSelection(SelectionEvent event) {
                List<FrozenGridModel> selected = lockedGrid.getSelectionModel().getSelectedItems();
                gridSelectionModel.doSelect(selected, false, true);
            }
        });
        grid.getSelectionModel().addSelectionHandler(new SelectionHandler() {
            @Override
            public void onSelection(SelectionEvent event) {
                List<FrozenGridModel> selected = grid.getSelectionModel().getSelectedItems();
                lockedGridSelectionModel.doSelect(selected, false, true);
            }
        });
    }

    public void setModel(List<FrozenGridModel> items) {
        store.clear();
        store.addAll(items);
    }
}