package com.gammadevs.world.animation;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
* Created with IntelliJ IDEA.
* User: Anton
* Date: 7/14/13
* Time: 1:20 PM
* To change this template use File | Settings | File Templates.
*/
public class AnimationTickActionListener implements ActionListener {

    public final static AnimationTickActionListener INSTANCE = new AnimationTickActionListener();
    private final List<Animation> animations = new CopyOnWriteArrayList<>();

    private AnimationTickActionListener() {}

    @Override
    public synchronized void actionPerformed(ActionEvent e) {
        List<Animation> toRemove = new LinkedList<>();
        for (Animation animation : animations) {
            if (!animation.isAnimationFinished()) {
                animation.incrementProgressAndRepaint();
            } else {
            	animation.animationProgressFinished();
                toRemove.add(animation);
            }
        }
        animations.removeAll(toRemove);
    }

    public synchronized boolean addAnimation(Animation animation) {
        return animations.add(animation);
    }

    public synchronized boolean removeAnimation(Animation animation) {
    	if (animation != null) {
	    	animation.animationProgressFinished();
	        return animations.remove(animation);
    	}
    	return false;
    }

}
