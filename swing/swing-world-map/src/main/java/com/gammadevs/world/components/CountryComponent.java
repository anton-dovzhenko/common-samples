package com.gammadevs.world.components;

import com.gammadevs.world.Constants;
import com.gammadevs.world.Country;
import com.gammadevs.world.animation.AnimationFinishedCallback;
import com.gammadevs.world.animation.AnimationSpeed;
import com.gammadevs.world.animation.CountryUpdateAnimation;

import javax.swing.*;

import java.awt.*;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;

@SuppressWarnings("serial")
public class CountryComponent extends JComponent {

    private final Country country;
    private Path2D path = new Path2D.Double();
    private CountryComponentStateEnum state = CountryComponentStateEnum.NORMAL;
    private boolean active;
    private NumberComponent numberComponent;
    private float opacity = 1;

    public CountryComponent(Country country) {
        this(country, 1);
    }

    public CountryComponent(Country country, double scale) {
        this.country = country;
        setSize(country.getCountryBounds().width, country.getCountryBounds().height);
        scale(scale);
        setLayout(null);
    }

    public void addNumberComponent(NumberComponent numberComponent) {
        this.numberComponent = numberComponent;
    }
    
    public void setOpacity(float opacity) {
    	this.opacity = opacity;
    }
    
    public void sendUpdate(int value) {
    	numberComponent.setValue(value);
    	state = CountryComponentStateEnum.UPDATE;
    	numberComponent.stopAnimation();
    	CountryUpdateAnimation animation = new CountryUpdateAnimation(AnimationSpeed.VERY_LOW, this, numberComponent);
    	animation.addAnimaitonFinishedCallback(new AnimationFinishedCallback() {
			@Override
			public void execute() {
				numberComponent.setVisible(false);
				CountryComponent.this.opacity = 1;
				CountryComponent.this.state = CountryComponentStateEnum.NORMAL;
				CountryComponent.this.repaint();
			}   		
    	});
    	animation.start();
    }

    public void scale(double scale) {
        path = new Path2D.Double();
        Point2D.Double[] vertices = country.getVertices();
        path.moveTo(vertices[0].getX() * scale, vertices[0].getY() * scale);
        for (int i = 1; i < vertices.length - 1; i++) {
            if (vertices[i] == Constants.CLOSE_PATH_VERTEX) {
                path.closePath();
            } else if (vertices[i - 1] == Constants.CLOSE_PATH_VERTEX) {
                path.moveTo(vertices[i].getX() * scale, vertices[i].getY() * scale);
            } else {
                path.lineTo(vertices[i].getX() * scale, vertices[i].getY() * scale);
            }
        }
        setBounds((int) (country.getCountryBounds().x * scale)
                , (int) (country.getCountryBounds().y * scale)
                , (int) (country.getCountryBounds().width * scale)
                , (int) (country.getCountryBounds().height * scale));
        if (numberComponent != null) {
            numberComponent.scale(scale);
            if (active) {
                numberComponent.startAnimation();
            }
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(active ? Constants.COUNTRY_SELECTED_COLOR : Constants.COUNTRY_DEFAULT_COLOR);
        g2.fill(path);
        if (state == CountryComponentStateEnum.UPDATE) {
        	 g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
             g2.setColor(Constants.COUNTRY_UPDATE_COLOR);
             g2.fill(path);
        }
        g2.setColor(Constants.COUNTRY_BORDER_COLOR);
        g2.draw(path);
    }
    
    @Override
    public boolean contains(int x, int y) {
        return path != null && path.contains(x, y);
    }

    public void mouseEntered() {
    	active = true;
        repaint();
        numberComponent.startAnimation();
    }

    public void mouseExited() {
    	active = false;
    	if (state != CountryComponentStateEnum.UPDATE) {
	        repaint();
	        numberComponent.stopAnimation();
        }
    }
   

}
