package com.gammadevs.gwt.gxtwidgets.client.widgets;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.shared.HandlerRegistration;
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
import com.sencha.gxt.data.shared.loader.LoadResultListStoreBinding;
import com.sencha.gxt.data.shared.loader.PagingLoadConfig;
import com.sencha.gxt.data.shared.loader.PagingLoadResult;
import com.sencha.gxt.data.shared.loader.PagingLoader;
import com.sencha.gxt.widget.core.client.container.HorizontalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.event.BodyScrollEvent;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.toolbar.PagingToolBar;

import java.util.List;

public class FrozenPagingGrid<T> implements IsWidget {

    interface FrozenGridExampleUiBinder extends UiBinder<VerticalLayoutContainer, FrozenPagingGrid> {}
    private static FrozenGridExampleUiBinder ourUiBinder = GWT.create(FrozenGridExampleUiBinder.class);

    private final FrozenGridSelectionModel<T> lockedGridSelectionModel;
    private final FrozenGridSelectionModel<T> gridSelectionModel;
    private final VerticalLayoutContainer rootElement;
    private HandlerRegistration lockedGridHandlerRegistration;
    private HandlerRegistration gridHandlerRegistration;

    @UiField(provided = true)
    HorizontalLayoutContainer.HorizontalLayoutData lockedLayoutData;
    @UiField(provided = true)
    Grid<T> lockedGrid;
    @UiField(provided = true)
    Grid<T> grid;
    @UiField
    PagingToolBar toolBar;

    public FrozenPagingGrid(
            ModelKeyProvider<T> key
            , List<ColumnConfig<T, ?>> lockedColumns
            , List<ColumnConfig<T, ?>> columns
            , PagingLoader<PagingLoadConfig, PagingLoadResult<T>> loader) {
        ListStore<T> store = new ListStore<>(key);
        loader.addLoadHandler(new LoadResultListStoreBinding<PagingLoadConfig, T, PagingLoadResult<T>>(store));
        lockedGridSelectionModel = new FrozenGridSelectionModel<>();
        gridSelectionModel = new FrozenGridSelectionModel<>();

        ColumnModel<T> lockCm = new ColumnModel<>(lockedColumns);
        ColumnModel<T> cm = new ColumnModel<>(columns);

        lockedGrid = new Grid<T>(store, lockCm) {
            @Override
            protected Size adjustSize(Size size) {
                return new Size(size.getWidth() + XDOM.getScrollBarWidth(), size.getHeight());
            }
        };
        lockedGrid.setLoader(loader);
        lockedGrid.setSelectionModel(lockedGridSelectionModel);

        grid = new Grid<>(store, cm);
        grid.setLoader(loader);
        grid.setSelectionModel(gridSelectionModel);

        lockedLayoutData = new HorizontalLayoutContainer.HorizontalLayoutData(getLockedWidth(lockCm), 1);
        lockedLayoutData.setMargins(new Margins(0, 0, XDOM.getScrollBarWidth(), 0));

        lockedGrid.getView().setAdjustForHScroll(false);

        synchronizeScrolling();
        synchronizeSelection();

        rootElement = ourUiBinder.createAndBindUi(this);
        toolBar.bind(loader);
        loader.load();

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
        lockedGridHandlerRegistration = lockedGrid.getSelectionModel().addSelectionHandler(new SelectionHandler<T>() {
            @Override
            public void onSelection(SelectionEvent event) {
                List<T> selected = lockedGrid.getSelectionModel().getSelectedItems();
                gridSelectionModel.doSelect(selected, false, true);
            }
        });
        gridHandlerRegistration = grid.getSelectionModel().addSelectionHandler(new SelectionHandler<T>() {
            @Override
            public void onSelection(SelectionEvent event) {
                List<T> selected = grid.getSelectionModel().getSelectedItems();
                lockedGridSelectionModel.doSelect(selected, false, true);
            }
        });
    }

    public void stop() {
        lockedGridHandlerRegistration.removeHandler();
        gridHandlerRegistration.removeHandler();
    }

}