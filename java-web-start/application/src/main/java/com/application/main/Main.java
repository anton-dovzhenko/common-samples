package com.application.main;

import javax.swing.*;
import java.awt.*;

/**
 * User: Anton Dovzhenko
 */
public class Main {
    
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 200);
        frame.add(new TextField("Hello World"));
        frame.setVisible(true);
    }
    
}
