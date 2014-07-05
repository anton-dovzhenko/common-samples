package com.gammadevs.pingpong.model;

/**
 * Created by Anton on 7/5/2014.
 */
public class Ball {

    private final int size;
    private final int speed;

    private int x;
    private int y;
    private int velocityX;
    private int velocityY;
    private int angle;

    public Ball(int x, int y, int size, int speed, int angle) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.speed = speed;
        recalculateVelocityAndSetAngle(angle);
    }

    public void recalculateVelocityAndSetAngle(int angle) {
        this.angle = angle;
        this.velocityX = (int) (Math.cos(angle * Math.PI / 180) * speed);
        this.velocityY = (int) (Math.sin(angle * Math.PI / 180) * speed);
    }

    public void updatePosition() {
        x += velocityX;
        y += velocityY;
    }

    public int getVelocityX() {
        return velocityX;
    }

    public int getVelocityY() {
        return velocityY;
    }

    public int getAngle() {
        return angle;
    }

    public int getSize() {
        return size;
    }

    public int getSpeed() {
        return speed;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
