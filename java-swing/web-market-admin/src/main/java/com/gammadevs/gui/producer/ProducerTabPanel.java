package com.gammadevs.gui.producer;

import com.gammadevs.dao.DbStorage;
import com.gammadevs.model.Producer;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by Anton on 9/14/2014.
 */
public class ProducerTabPanel extends JPanel {

    private final DbStorage storage = DbStorage.getInstance();
    private JTable table;
    private AddOrUpdateProducerPanel addPanel;

    public ProducerTabPanel() {
        super(new GridLayout(2,0));
        createUi();
        initData();
        initListeners();
    }

    private void createUi() {
        table = new JTable(new DefaultTableModel(new Object[]{"Id", "Name"}, 0) {
            @Override
            public boolean isCellEditable(int rowIndex, int mColIndex) {
                return false;
            }
        });
        table.getColumnModel().getColumn(0).setMaxWidth(50);
        addPanel = new AddOrUpdateProducerPanel();
        this.add(new JScrollPane(table));
        this.add(addPanel);
    }

    private void initData() {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        for (Object[] row : DbStorage.getInstance().getProducersAsTable()) {
            model.addRow(row);
        }
    }

    private void initListeners() {
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int row = table.getSelectedRow();
                    Long id = (Long) table.getModel().getValueAt(row, 0);
                    String name = (String) table.getModel().getValueAt(row, 1);
                    addPanel.setProducer(new Producer(id, name));
                }
            }
        });
        addPanel.addSaveBtnListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Producer producer = storage.saveOrUpdate(addPanel.getProducer());
                addPanel.clear();
                addOrUpdateTableRow(producer);
            }
        });
    }

    private void addOrUpdateTableRow(Producer producer) {
        for (int i = 0; i < table.getRowCount(); i++) {
            if (table.getValueAt(i, 0).equals(producer.getId())) {
                table.setValueAt(producer.getName(), i, 1);
                return;
            }
        }
        ((DefaultTableModel) table.getModel()).addRow(new Object[] {producer.getId(), producer.getName()});
    }

}
