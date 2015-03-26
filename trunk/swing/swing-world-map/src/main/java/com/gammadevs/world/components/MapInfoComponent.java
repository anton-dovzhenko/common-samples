package com.gammadevs.world.components;

import com.gammadevs.world.animation.AnimationFinishedCallback;
import com.gammadevs.world.animation.AnimationSpeed;
import com.gammadevs.world.scaling.Scalable;

public interface MapInfoComponent extends Scalable {

    void startAnimation();
    void startAnimation(AnimationSpeed speed, AnimationFinishedCallback callback);
    void stopAnimation();
    void setValue(int value);
    void setVisible(boolean visible);
}
