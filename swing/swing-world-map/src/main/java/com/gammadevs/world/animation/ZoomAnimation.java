package com.gammadevs.world.animation;

import com.gammadevs.world.components.CountryComponent;

import javax.swing.*;

import java.awt.*;
import java.util.Collection;

public class ZoomAnimation extends AbstractAnimation {

    private JScrollPane scrollPane;
    private Collection<CountryComponent> countryComponents;
    private Point previousViewPosition;
    private double startScale;
    private double finishScale;
    private int width;
    private int height;
    private double ex;
    private double ey;

    public ZoomAnimation(AnimationSpeed speed, JScrollPane scrollPane, Collection<CountryComponent> countryComponents
            , double startScale, double finishScale
            , int width, int height, double ex, double ey) {
    	super(speed);
        this.scrollPane = scrollPane;
        this.countryComponents = countryComponents;
        this.previousViewPosition = scrollPane.getViewport().getViewPosition();
        this.startScale = startScale;
        this.finishScale = finishScale;
        this.width = width;
        this.height = height;
        this.ex = ex;
        this.ey = ey;
    }
    
    public ZoomAnimation(JScrollPane scrollPane, Collection<CountryComponent> countryComponents
                         , double startScale, double finishScale
            , int width, int height, double ex, double ey) {
        this(AnimationSpeed.HIGH, scrollPane, countryComponents, startScale, finishScale, width, height, ex, ey);
    }

    @Override
    protected void repaint() {
        double scale = startScale + (finishScale - startScale) * getProgressInPct();
        final int worldMapWidth = (int) (width * scale);
        final int worldMapHeight = (int) (height * scale);

        for (CountryComponent countryComponent : countryComponents) {
            countryComponent.scale(scale);
        }
        double x = ex / startScale * scale;
        double y = ey / startScale * scale;
        double pointX = 0;
        double pointY = 0;
        if (x - width / 2 > 0) {
            pointX = x - width / 2;
            if (width * (scale - 1) < pointX) {
                pointX = width * (scale - 1);
            }
        }
        if (y - height / 2 > 0) {
            pointY = y - height / 2;
            if (height * (scale - 1) < pointY) {
                pointY = height * (scale - 1);
            }
        }
        //TODO: verify the logic
        pointX = previousViewPosition.getX() + (pointX - previousViewPosition.getX()) * getProgressInPct();
        pointY = previousViewPosition.getY() + (pointY - previousViewPosition.getY()) * getProgressInPct();
        scrollPane.getViewport().getView().setPreferredSize(new Dimension(worldMapWidth, worldMapHeight));
        scrollPane.getViewport().getView().setSize(worldMapWidth, worldMapHeight);
        scrollPane.getViewport().setViewPosition(new Point((int) pointX, (int) pointY));
    }
}
