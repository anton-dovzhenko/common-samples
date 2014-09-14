package com.gammadevs;

import com.gammadevs.gui.MainLayout;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Anton on 9/14/2014.
 */
public class Main {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                JFrame frame = new JFrame("WebMarket Admin");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                MainLayout mainLayout = new MainLayout();

                frame.getContentPane().add(mainLayout, BorderLayout.CENTER);
                final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
                frame.setLocation((screenSize.width - mainLayout.getPreferredSize().width) / 2
                        , (screenSize.height - mainLayout.getPreferredSize().height) / 2);
                frame.pack();
                frame.setVisible(true);
            }

        });

    }

}
