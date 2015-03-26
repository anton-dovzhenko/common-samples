package com.gammadevs.world.components;

import com.gammadevs.world.Constants;
import com.gammadevs.world.Country;
import com.gammadevs.world.animation.AbstractAnimation;
import com.gammadevs.world.animation.AnimationFinishedCallback;
import com.gammadevs.world.animation.AnimationSpeed;
import com.gammadevs.world.animation.NumberComponentAnimation;

import javax.swing.*;

import java.awt.*;

@SuppressWarnings("serial")
public class NumberComponent extends JComponent {

    private final static int RAD = 4;
    private AbstractAnimation animation;
    private int size;
    private int value;
    private final Country country;
    private float opacity = 1;

    public NumberComponent(Country country, int size, int value) {
        this.size = size;
        this.value = value;
        this.country = country;
        init();
    }

    private void init() {
        setVisible(false);
        setSize(size, size);
        scale(1);
    }
    
    public void setBoundsWithinCountry(int x, int y, int width, int height) {
        int posX = size >= width ? x : x + (width - size) / 2;
        int posY = size >= height ? y : y + (height - size) / 2;
        setBounds(posX, posY, size, size);
    }
    
    public void setValue(int value) {
    	this.value = value;
    }
    
    public void setOpacity(float opacity) {
    	this.opacity = opacity;
    }

    public void startAnimation() {
    	startAnimation(AnimationSpeed.NORMAL, null);
    }
    
	public void startAnimation(AnimationSpeed speed, AnimationFinishedCallback callback) {
    	if (!isAnimationActive()) {
            animation = new NumberComponentAnimation(speed, this);
            if (callback != null) {
            	animation.addAnimaitonFinishedCallback(callback);
            }
            animation.start();
        }
	}

    public void stopAnimation() {
        if (animation != null) {
        	animation.stop();
        	animation = null;
        }
    }

    private boolean isAnimationActive() {
        return animation != null && !animation.isAnimationFinished();
    }

    public void scale(double scale) {
        stopAnimation();
        setBoundsWithinCountry((int) (country.getCountryBounds().x * scale)
                , (int) (country.getCountryBounds().y * scale)
                , (int) (country.getCountryBounds().width * scale)
                , (int) (country.getCountryBounds().height * scale));
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
        g2.setColor(Color.BLACK);
        g2.fillOval(0, 0, size, size);
        g2.setColor(Constants.NUMBER_COMP_INNER_CIRCLE);
        g2.fillOval(RAD, RAD, size - 2 * RAD, size - 2 * RAD);
        g2.setColor(Color.WHITE);
        g2.setFont(g2.getFont().deriveFont(Font.BOLD));
        FontMetrics fm = getFontMetrics(g2.getFont());
        String text = value + "";
        fm.stringWidth(text);
        g2.drawString(text, (size - fm.stringWidth(text)) / 2, (size + fm.getHeight()) / 2 - 3);
    }

}
