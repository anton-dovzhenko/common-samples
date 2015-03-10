package com.gammadevs.gwt.gxtwidgets.client.widgets;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.core.client.Style;
import com.sencha.gxt.core.client.dom.XDOM;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.core.client.util.Size;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.widget.core.client.container.HorizontalLayoutContainer;
import com.sencha.gxt.widget.core.client.event.BodyScrollEvent;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.grid.GridSelectionModel;

import java.util.List;

public class FrozenGrid<T> implements IsWidget {

    private static class FrozenGridSelectionModel<M> extends GridSelectionModel<M> {
        @Override
        public void doSelect(List<M> models, boolean keepExisting, boolean suppressEvent) {
            super.doSelect(models, keepExisting, suppressEvent);
        }

    }

    interface FrozenGridExampleUiBinder extends UiBinder<HorizontalLayoutContainer, FrozenGrid> {}
    private static FrozenGridExampleUiBinder ourUiBinder = GWT.create(FrozenGridExampleUiBinder.class);

    private final ListStore<T> store;
    private final FrozenGridSelectionModel<T> lockedGridSelectionModel;
    private final FrozenGridSelectionModel<T> gridSelectionModel;
    private final HorizontalLayoutContainer rootElement;

    @UiField(provided = true)
    HorizontalLayoutContainer.HorizontalLayoutData lockedLayoutData;
    @UiField(provided = true)
    Grid<T> lockedGrid;
    @UiField(provided = true)
    Grid<T> grid;

    public FrozenGrid(
            ModelKeyProvider<T> key
            , List<ColumnConfig<T, ?>> lockedColumns
            , List<ColumnConfig<T, ?>> columns) {
        store = new ListStore<>(key);
        lockedGridSelectionModel = new FrozenGridSelectionModel<>();
        gridSelectionModel = new FrozenGridSelectionModel<>();

        ColumnModel<T> lockCm = new ColumnModel<>(lockedColumns);
        ColumnModel<T> cm = new ColumnModel<>(columns);

        lockedGrid = new Grid<T>(store, lockCm) {
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

    }

    @Override
    public Widget asWidget() {
        return rootElement;
    }

    private double getLockedWidth(ColumnModel<T> columnModel) {
        double width = 0;
        for (ColumnConfig<T, ?> column : columnModel.getColumns()) {
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
                List<T> selected = lockedGrid.getSelectionModel().getSelectedItems();
                gridSelectionModel.doSelect(selected, false, true);
            }
        });
        grid.getSelectionModel().addSelectionHandler(new SelectionHandler() {
            @Override
            public void onSelection(SelectionEvent event) {
                List<T> selected = grid.getSelectionModel().getSelectedItems();
                lockedGridSelectionModel.doSelect(selected, false, true);
            }
        });
    }

    public void setModel(List<T> items) {
        store.clear();
        store.addAll(items);
    }
}