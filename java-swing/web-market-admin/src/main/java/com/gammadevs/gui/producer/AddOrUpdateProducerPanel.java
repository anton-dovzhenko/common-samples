package com.gammadevs.gui.producer;

import com.gammadevs.model.Producer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Anton on 9/14/2014.
 */
public class AddOrUpdateProducerPanel extends JPanel {

    private JTextField idTextField;
    private JTextField nameTextField;
    private JButton cancelButton;
    private JButton saveButton;

    private Producer producer;

    public AddOrUpdateProducerPanel() {
        super(new GridBagLayout());
        createUi();
        initListeners();
    }

    private void createUi() {
        idTextField = new JTextField();
        idTextField.setEnabled(false);
        idTextField.setText("");
        nameTextField = new JTextField();
        cancelButton = new JButton("Cancel");
        cancelButton.setPreferredSize(new Dimension(75, 20));
        saveButton = new JButton("Save");
        saveButton.setPreferredSize(new Dimension(75, 20));

        GridBagConstraints c = new GridBagConstraints();
        c.ipady = 5;
        c.weightx = 0.2;
        c.gridx = 0;
        c.gridy = 0;
        c.fill = GridBagConstraints.NONE;
        c.insets = new Insets(10, 20, 0, 0);
        c.anchor = GridBagConstraints.WEST;
        c.gridwidth = 1;
        this.add(new JLabel("Id:"), c);

        c.weightx = 0.8;
        c.gridx = 1;
        c.gridy = 0;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(0, 0, 0, 20);
        c.anchor = GridBagConstraints.WEST;
        c.gridwidth = 3;
        this.add(idTextField, c);

        c.weightx = 0.2;
        c.gridx = 0;
        c.gridy = 1;
        c.insets = new Insets(10, 20, 0, 0);
        c.fill = GridBagConstraints.NONE;
        c.anchor = GridBagConstraints.WEST;
        c.gridwidth = 1;
        this.add(new JLabel("Name:"), c);

        c.weightx = 0.8;
        c.gridx = 1;
        c.gridy = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(0, 0, 0, 20);
        c.anchor = GridBagConstraints.WEST;
        c.gridwidth = 3;
        this.add(nameTextField, c);

        c.weightx = 1;
        c.gridx = 2;
        c.gridy = 2;
        c.fill = GridBagConstraints.WEST;
        c.insets = new Insets(0, 20, 0, 20);
        c.gridwidth = 1;
        this.add(cancelButton, c);

        c.weightx = 1;
        c.gridx = 3;
        c.gridy = 2;
        c.fill = GridBagConstraints.EAST;
        c.insets = new Insets(0, 20, 0, 20);
        c.gridwidth = 1;
        this.add(saveButton, c);

        c.weightx = 1;
        c.gridx = 3;
        c.gridy = 3;
        c.fill = GridBagConstraints.EAST;
        c.insets = new Insets(0, 20, 0, 20);
        c.gridwidth = 1;
    }

    private void initListeners() {
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clear();
            }
        });
    }

    void addSaveBtnListener(ActionListener listener) {
        saveButton.addActionListener(listener);
    }

    //TODO: implement error logic and add error Label.
    void setErrorMessage(String text) {

    }

    void setProducer(Producer producer) {
        this.producer = producer;
        idTextField.setText(producer.getId().toString());
        nameTextField.setText(producer.getName());
    }

    void clear() {
        producer = null;
        idTextField.setText("");
        nameTextField.setText("");
    }

    Producer getProducer() {
        return new Producer(producer != null ? producer.getId() : null
                , nameTextField.getText());
    }

}
