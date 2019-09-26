package com.example.bullettime;

import android.content.Context;

public class Square {

    private int xPosition;
    private int yPosition;
    private int width;

    public Square(Context context, int x, int y, int width) {
        this.xPosition = x;
        this.yPosition = y;
        this.width = width;
    }

    public int getxPosition() {
        return xPosition;
    }

    public void setxPosition(int xPosition) {
        this.xPosition = xPosition;
    }

    public int getyPosition() {
        return yPosition;
    }

    public void setyPosition(int yPosition) {
        this.yPosition = yPosition;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }
}

