package com.gammadevs.world.utils;

import javax.swing.*;

public final class LookAndFeelUtils {

    private LookAndFeelUtils() {}

    public static void setNimbusLokAndFeel() {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (ClassNotFoundException|InstantiationException|IllegalAccessException|UnsupportedLookAndFeelException e) {
            System.out.println("Nimbus L&F can't be set");
            e.printStackTrace();
        }
    }
    
    public static void turnOnAntialiasing() {
		System.setProperty("awt.useSystemAAFontSettings","on");
		System.setProperty("swing.aatext", "true");
    }
}
