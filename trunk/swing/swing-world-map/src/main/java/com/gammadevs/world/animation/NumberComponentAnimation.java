package com.gammadevs.world.animation;

import com.gammadevs.world.components.NumberComponent;

public class NumberComponentAnimation extends AbstractAnimation {

    private final NumberComponent numberComponent;

    public NumberComponentAnimation(NumberComponent numberComponent) {
        this(AnimationSpeed.NORMAL, numberComponent);
    }
    
    public NumberComponentAnimation(AnimationSpeed speed, NumberComponent numberComponent) {
    	super(speed);
        this.numberComponent = numberComponent;
    }

    @Override
    protected void repaint() {
    	numberComponent.setOpacity((float) getProgressInPct());
        numberComponent.setVisible(true);
        numberComponent.repaint();
    }
    
    @Override
    public void stop() {
    	numberComponent.setVisible(false);
    	super.stop();
    }

}
