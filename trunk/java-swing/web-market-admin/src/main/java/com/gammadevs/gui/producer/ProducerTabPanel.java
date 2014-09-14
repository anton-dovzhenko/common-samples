package com.gammadevs.gui.producer;

import com.gammadevs.dao.DbStorage;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * Created by Anton on 9/14/2014.
 */
public class ProducerTabPanel extends JPanel {

    private JTable table;
    private AddProducerPanel addPanel;

    public ProducerTabPanel() {
        super(new GridLayout(2,0));
        createUi();
        initData();
    }

    private void createUi() {
        table = new JTable(new DefaultTableModel(new Object[]{"Id", "Name"}, 0));
        addPanel = new AddProducerPanel();
        this.add(new JScrollPane(table));
        this.add(addPanel);
    }

    private void initData() {
        DbStorage.getInstance().getProducersAsTable();
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        for (Object[] row : DbStorage.getInstance().getProducersAsTable()) {
            model.addRow(row);
        }
    }

}
