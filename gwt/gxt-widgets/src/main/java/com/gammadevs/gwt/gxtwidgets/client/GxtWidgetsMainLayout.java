package com.gammadevs.gwt.gxtwidgets.client;

import com.gammadevs.gwt.gxtwidgets.client.examples.CheckComboBoxExample;
import com.gammadevs.gwt.gxtwidgets.client.examples.FrozenGridExample;
import com.gammadevs.gwt.gxtwidgets.client.examples.FrozenPagingGridExample;
import com.gammadevs.gwt.gxtwidgets.client.widgets.ExampleTreeItem;
import com.google.gwt.cell.client.Cell;
import com.google.gwt.cell.client.ValueUpdater;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.text.shared.SimpleSafeHtmlRenderer;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiFactory;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.cell.core.client.SimpleSafeHtmlCell;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.TreeStore;
import com.sencha.gxt.widget.core.client.PlainTabPanel;
import com.sencha.gxt.widget.core.client.TabItemConfig;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
import com.sencha.gxt.widget.core.client.event.CloseEvent;
import com.sencha.gxt.widget.core.client.tree.Tree;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class GxtWidgetsMainLayout implements IsWidget {

    interface GxtWidgetsMainLayoutUiBinder extends UiBinder<BorderLayoutContainer, GxtWidgetsMainLayout> {}
    private static GxtWidgetsMainLayoutUiBinder ourUiBinder = GWT.create(GxtWidgetsMainLayoutUiBinder.class);

    private final BorderLayoutContainer rootElement;
    private final Map<ExampleType, Widget> exampleWidgets = new HashMap<>();

    @UiField(provided = true)
    TreeStore<ExampleTreeItem> store = new TreeStore<>(new ModelKeyProvider<ExampleTreeItem>() {
        @Override
        public String getKey(ExampleTreeItem item) {
            return item.getName();
        }
    });
    @UiField
    Tree<ExampleTreeItem, String> tree;
    @UiField
    PlainTabPanel tabPanel;

    public GxtWidgetsMainLayout() {
        rootElement = ourUiBinder.createAndBindUi(this);
        initExampleTreeModel();
        tabPanel.addCloseHandler(new CloseEvent.CloseHandler<Widget>() {
            @Override
            public void onClose(CloseEvent<Widget> event) {
                Iterator<Map.Entry<ExampleType, Widget>> iterator = exampleWidgets.entrySet().iterator();
                while (iterator.hasNext()) {
                    Map.Entry<ExampleType, Widget> pair = iterator.next();
                    if (pair.getValue() == event.getItem()) {
                        iterator.remove();
                        break;
                    }
                }
            }
        });
    }

    @UiFactory
    public Cell<String> createCell() {
        return new SimpleSafeHtmlCell<String>(SimpleSafeHtmlRenderer.getInstance(), "click") {
            @Override
            public void onBrowserEvent(Context context, Element parent, String value, NativeEvent event
                    , ValueUpdater<String> valueUpdater) {
                super.onBrowserEvent(context, parent, value, event, valueUpdater);
                if ("click".equals(event.getType())) {
                    ExampleTreeItem item = tree.getSelectionModel().getSelectedItem();
                    if (item.getType() != null) {
                        addTab(item);
                    }
                }
            }
        };
    }

    private void addTab(ExampleTreeItem item) {
        if (exampleWidgets.containsKey(item.getType())) {
            tabPanel.setActiveWidget(exampleWidgets.get(item.getType()));
            return;
        }
        Widget widget;
        switch (item.getType()) {
            case FROZEN_GRID:
                widget = new FrozenGridExample().asWidget();
                break;
            case FROZEN_PAGING_GRID:
                widget = new FrozenPagingGridExample().asWidget();
                break;
            case CHECKED_COMBO_BOX:
                widget = new CheckComboBoxExample().asWidget();
                break;
            default:
                throw new IllegalArgumentException("Type " + item.getType() + " is not supported");
        }
        TabItemConfig config = new TabItemConfig(item.getName());
        config.setClosable(true);
        tabPanel.add(widget, config);
        tabPanel.setActiveWidget(widget);
        exampleWidgets.put(item.getType(), widget);


    }

    @UiFactory
    public ValueProvider<ExampleTreeItem, String> createValueProvider() {
        return new ValueProvider<ExampleTreeItem, String>() {
            @Override
            public String getValue(ExampleTreeItem object) {
                return object.getName();
            }

            @Override
            public void setValue(ExampleTreeItem object, String value) {}
            @Override
            public String getPath() {
                return "name";
            }
        };
    }

    @Override
    public Widget asWidget() {
        return rootElement;
    }

    private void initExampleTreeModel() {
        ExampleTreeItem comboRoot = new ExampleTreeItem("Charts", null);
        store.add(comboRoot);
        store.add(comboRoot, new ExampleTreeItem("Time Series Chart", ExampleType.TIME_SERIES_CHART));
        ExampleTreeItem gridRoot = new ExampleTreeItem("Grids", null);
        store.add(gridRoot);
        store.add(gridRoot, new ExampleTreeItem("Frozen Columns Grid", ExampleType.FROZEN_GRID));
        store.add(gridRoot, new ExampleTreeItem("Frozen Columns Paging Grid", ExampleType.FROZEN_PAGING_GRID));
    }

}