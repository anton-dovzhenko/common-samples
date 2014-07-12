package com.gammadevs.pingpong.model;

/**
 * Created by Anton on 7/5/2014.
 */
public class Player {

    private int height;
    private int width;
    private int padding;
    private int left;

    public Player(int height, int width, int padding, int left) {
        this.height = height;
        this.width = width;
        this.padding = padding;
        this.left = left;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getPadding() {
        return padding;
    }

    public void setPadding(int padding) {
        this.padding = padding;
    }

    public int getLeft() {
        return left;
    }

    public void setLeft(int left) {
        this.left = left;
    }
}
