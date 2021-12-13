package com.nick.Furnitures;

import java.awt.*;

public class FurnitureComponent {

    public int getXOffset() {
        return xOffset;
    }

    public int getYOffset() {
        return yOffset;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public Color getColor() {
        return color;
    }

    public void setXOffset(int xOffset) {
        this.xOffset = xOffset;
    }

    public void setYOffset(int yOffset) {
        this.yOffset = yOffset;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public boolean isOutline() {
        return isOutline;
    }

    public boolean isPattern() {
        return isPattern;
    }

    private int xOffset, yOffset;
    private double width, height;
    private Color color;
    private boolean isOutline;
    private boolean isPattern;

    public FurnitureComponent(int xOffset, int yOffset, double width, double height, Color color, boolean isOutline, boolean isPattern) {
        this.xOffset = xOffset;
        this.yOffset = yOffset;
        this.width = width;
        this.height = height;
        this.color = color;
        this.isOutline = isOutline;
        this.isPattern = isPattern;
    }

}
