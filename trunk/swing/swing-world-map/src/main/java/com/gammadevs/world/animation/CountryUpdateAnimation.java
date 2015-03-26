package com.gammadevs.world.animation;

import com.gammadevs.world.components.CountryComponent;
import com.gammadevs.world.components.NumberComponent;

public class CountryUpdateAnimation extends AbstractAnimation {
	
	private final CountryComponent countryComponent;
	private final NumberComponent numberComponent;
	
	public CountryUpdateAnimation(AnimationSpeed speed
			, CountryComponent countryComponent, NumberComponent numberComponent) {
		super(speed);
		this.countryComponent = countryComponent;
		this.numberComponent = numberComponent;
	}

	@Override
	protected void repaint() {
		float progress = (float) getProgressInPct();
		progress = progress <= 0.5 ? progress * 2 : (1 - progress) * 2;
		countryComponent.setOpacity(progress);
		numberComponent.setOpacity(progress);
        numberComponent.setVisible(true);
        numberComponent.repaint();
        countryComponent.repaint();
	}
	
	@Override
    public void start() {
		numberComponent.setOpacity(0);
		numberComponent.setVisible(true);
		super.start();
    }
	
	public void stop() {
		numberComponent.setVisible(false);
		countryComponent.setOpacity(1);
		countryComponent.repaint();
    	super.stop();
    }

}
