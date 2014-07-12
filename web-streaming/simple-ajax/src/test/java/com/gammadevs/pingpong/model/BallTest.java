package com.gammadevs.pingpong.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class BallTest {

    @Test
    public void testRecalculateVelocityAndSetAngle() throws Exception {
        Ball ball = new Ball(0, 0, 30, 4, 45);
        assertEquals(45, ball.getAngle());
        assertEquals(2, ball.getVelocityX());
        assertEquals(2, ball.getVelocityY());
    }
}