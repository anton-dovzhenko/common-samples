package com.gammadevs.world.animation;

/**
* Created with IntelliJ IDEA.
* User: Anton
* Date: 7/14/13
* Time: 1:20 PM
* To change this template use File | Settings | File Templates.
*/
public interface Animation {
    void incrementProgressAndRepaint();
    boolean isAnimationFinished();
    int getProgress();
    double getProgressInPct();
    void animationProgressFinished();
    boolean addAnimaitonFinishedCallback(AnimationFinishedCallback callback);
    boolean removeAnimaitonFinishedCallback(AnimationFinishedCallback callback);
    void start();
    void stop();
}
