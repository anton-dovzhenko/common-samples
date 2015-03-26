package com.gammadevs.world.listeners;

import com.gammadevs.world.Constants;
import com.gammadevs.world.components.CountryComponent;
import com.gammadevs.world.animation.AbstractAnimation;
import com.gammadevs.world.animation.AnimationSpeed;
import com.gammadevs.world.animation.ZoomAnimation;
import com.gammadevs.world.utils.MathUtils;

import javax.swing.*;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.Collection;

public class WorldMapMouseListener extends MouseAdapter {

    private double scale;
    private final JScrollPane worldMapScrollPane;
    private final Collection<CountryComponent> countryComponents;
    private AbstractAnimation animation;
    private Point lastPressedPoint;

    public WorldMapMouseListener(double scale, JScrollPane worldMapScrollPane
            , Collection<CountryComponent> countryComponents) {
        this.scale = scale;
        this.worldMapScrollPane = worldMapScrollPane;
        this.countryComponents = countryComponents;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        lastPressedPoint = e.getPoint();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() == 2) {
            final double prevScale = scale;
            if (SwingUtilities.isLeftMouseButton(e)) {
                scale *= Constants.SCALE_INCREMENT;
            } else if (SwingUtilities.isRightMouseButton(e)) {
                if (scale > 1) {
                    scale /= Constants.SCALE_INCREMENT;
                }
            } else {
                return;
            }
            animate(AnimationSpeed.HIGH, prevScale, scale, e.getX(), e.getY());
        }
    }
    
    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
    	if (isAnimationActive()) {
    		return;
    	}
    	int scrollUnitCount = -e.getUnitsToScroll() / Constants.MOUSE_WHEEL_MOVED_THRESHOLD;
    	if (scrollUnitCount == 0) {
    		scrollUnitCount = (int) Math.signum(-e.getUnitsToScroll());
    	}
    	double prevScale = scale;
    	scale *= scrollUnitCount > 0 ? Math.pow(Constants.SCALE_INCREMENT, scrollUnitCount)
    			: Math.pow(Constants.SCALE_INCREMENT, scrollUnitCount);
    	if (scale < 1) {
    		scale = 1;
    	}
    	animate(AnimationSpeed.VERY_HIGH, prevScale, scale
    			, (int) (worldMapScrollPane.getViewport().getViewPosition().x + Constants.WORLD_MAP_WIDTH / 2)
    			, (int) (worldMapScrollPane.getViewport().getViewPosition().y + Constants.WORLD_MAP_HEIGHT / 2));
    }
    
    private void animate(AnimationSpeed speed, double prevScale, double scale, int x, int y) {
        if (isAnimationActive()) {
        	animation.stop();
        }
        animation = new ZoomAnimation(speed, worldMapScrollPane
                , countryComponents
                , prevScale
                , scale
                , Constants.WORLD_MAP_WIDTH
                , Constants.WORLD_MAP_HEIGHT
                , x
                , y
        );
        animation.start();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (!isAnimationActive()) {
            Point currentViewPosition = worldMapScrollPane.getViewport().getViewPosition();
            int x = (int) MathUtils.getBoundedValue(
                    currentViewPosition.getX() - e.getX() + lastPressedPoint.getX()
                    , 0
                    , worldMapScrollPane.getViewport().getView().getSize().getWidth() - Constants.WORLD_MAP_WIDTH);
            int y = (int) MathUtils.getBoundedValue(currentViewPosition.getY() - e.getY() + lastPressedPoint.getY()
                    , 0
                    , worldMapScrollPane.getViewport().getView().getSize().getHeight() - Constants.WORLD_MAP_HEIGHT);
            worldMapScrollPane.getViewport().setViewPosition(new Point(x, y));
            lastPressedPoint = new Point(e.getX(), e.getY());
        }
    }

    private boolean isAnimationActive() {
        return animation != null && !animation.isAnimationFinished();
    }
}
