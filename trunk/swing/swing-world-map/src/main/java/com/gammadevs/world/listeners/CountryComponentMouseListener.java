package com.gammadevs.world.listeners;

import com.gammadevs.world.components.CountryComponent;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CountryComponentMouseListener extends MouseAdapter {

    private final JScrollPane scrollPane;
    private final CountryComponent component;

    public CountryComponentMouseListener(JScrollPane scrollPane, CountryComponent component) {
        this.scrollPane = scrollPane;
        this.component = component;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        dispatchEventToParent(e
                , e.getX() + component.getBounds().getX() - scrollPane.getViewport().getViewPosition().getX()
                , e.getY() + component.getBounds().getY() - scrollPane.getViewport().getViewPosition().getY()
        );
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        component.mouseEntered();
    }

    @Override
    public void mouseExited(MouseEvent e) {
        component.mouseExited();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() == 2) {
            dispatchEventToParent(e, (int) (e.getX() + component.getBounds().getX())
                    , (int) (e.getY() + component.getBounds().getY()));
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        dispatchEventToParent(e
                , e.getX() + component.getBounds().getX() - scrollPane.getViewport().getViewPosition().getX()
                , e.getY() + component.getBounds().getY() - scrollPane.getViewport().getViewPosition().getY()
        );
    }

    private void dispatchEventToParent(MouseEvent e, double x, double y) {
        component.getParent().dispatchEvent(
                new MouseEvent(component
                        , e.getID()
                        , e.getWhen()
                        , e.getModifiers()
                        , (int) x
                        , (int) y
                        , e.getXOnScreen()
                        , e.getYOnScreen()
                        , e.getClickCount()
                        , e.isPopupTrigger()
                        , e.getButton()));
    }

}
