package com.gammadevs.gui;

import com.gammadevs.gui.category.CategoryTabPanel;
import com.gammadevs.gui.producer.ProducerTabPanel;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Anton on 9/14/2014.
 */
public class MainLayout extends JTabbedPane {

    private final Dimension size = new Dimension(500, 400);

    private final CategoryTabPanel categoryPanel = new CategoryTabPanel();
    private final JLabel commodityPanel = new JLabel("Commodity");
    private final ProducerTabPanel producerPanel = new ProducerTabPanel();

    public MainLayout() {
        createUi();
    }

    private void createUi() {
        this.setPreferredSize(size);

        this.addTab("Categories", categoryPanel);
        this.addTab("Producers", producerPanel);
        this.addTab("Commodities", commodityPanel);
    }

}
