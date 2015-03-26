package com.gammadevs.world.animation;

public enum AnimationSpeed {

	VERY_LOW(0.25)
	, LOW(0.5)
	, NORMAL(1)
	, HIGH(2)
	, VERY_HIGH(4);
	
	private final double value;
	
	AnimationSpeed(double value) {
		this.value = value;
	}

	public double getValue() {
		return value;
	}
	
}
