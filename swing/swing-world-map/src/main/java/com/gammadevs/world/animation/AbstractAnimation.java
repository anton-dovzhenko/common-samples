package com.gammadevs.world.animation;

import java.util.LinkedList;
import java.util.List;

public abstract class AbstractAnimation implements Animation {

    private static final int MAX_PROGRESS = 100;
    private double progress = 0;
    private AnimationSpeed speed = AnimationSpeed.NORMAL;
    private final List<AnimationFinishedCallback> animationFinishCallbacks = new LinkedList<>();

    public AbstractAnimation() {}

    public AbstractAnimation(AnimationSpeed speed) {
        this.speed = speed;
    }

    @Override
    public void incrementProgressAndRepaint() {
        progress += speed.getValue();
        if (progress > MAX_PROGRESS) {
            progress = MAX_PROGRESS;
        }
        repaint();
    }

    @Override
    public boolean isAnimationFinished() {
        return progress >= MAX_PROGRESS;
    }

    @Override
    public int getProgress() {
        return (int) progress;
    }

    @Override
    public double getProgressInPct() {
        return progress == 100 ? 1 : 1d * progress / MAX_PROGRESS;
    }
    
    @Override
    public void animationProgressFinished() {
    	for (AnimationFinishedCallback callback : animationFinishCallbacks) {
    		callback.execute();
    	}
    }
    
    @Override
    public boolean addAnimaitonFinishedCallback(AnimationFinishedCallback callback) {
    	return animationFinishCallbacks.add(callback);
    }
    
    @Override
    public boolean removeAnimaitonFinishedCallback(AnimationFinishedCallback callback) {
    	return animationFinishCallbacks.remove(callback);
    }
    
    @Override
    public void start() {
    	AnimationTickActionListener.INSTANCE.addAnimation(this);
    }
    
    @Override
    public void stop() {
    	AnimationTickActionListener.INSTANCE.removeAnimation(this);
    }

    protected abstract void repaint();
}
