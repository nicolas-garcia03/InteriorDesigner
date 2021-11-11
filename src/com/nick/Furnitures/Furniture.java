package com.nick.Furnitures;

import java.awt.*;

public abstract class Furniture {

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

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    private int x;
    private int y;
    private int width;
    private int height;

    private Furniture thingOnIt;

    public Furniture(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public Furniture getThingOnIt() {
        return thingOnIt;
    }

    public void setThingOnIt(Furniture thingOnIt) {
        this.thingOnIt = thingOnIt;
    }

    public abstract ID getFurnitureID();

    public abstract String getName();

    public double getFloorArea() {
        return width*height;
    }

    public abstract FurnitureComponent[] getComponents();

    protected FurnitureComponent[] getAllComponents(FurnitureComponent[] mainComponents) {

        if (thingOnIt == null) {
            return mainComponents;
        }

        FurnitureComponent[] components = new FurnitureComponent[mainComponents.length+thingOnIt.getComponents().length];

        for (int i = 0; i < mainComponents.length; i++) {
            components[i] = mainComponents[i];
        }

        for (int i = 0; i < thingOnIt.getComponents().length; i++) {
            components[i + mainComponents.length] = thingOnIt.getComponents()[i];
        }

        return components;
    }

    public boolean contains(int x1, int y1) {
        boolean c1 = x1 >= getX();
        boolean c2 = y1 >= getY();
        boolean c3 = x1 < getX() + getWidth();
        boolean c4 = y1 < getY() + getHeight();
        return c1 && c2 && c3 && c4;
    }

    public boolean intersects(int fx, int fy, int fw, int fh) {
        boolean c1 = x+width <= fx; //left of f
        boolean c2 = y+height <= fy; //above f
        boolean c3 = x >= fx + fw; // right of f
        boolean c4 = y >= fy + fh; // below f
        return !(c1 || c2 || c3 || c4);
    }

    public void move(char direction) {
        switch (direction) {
            case 'l' -> x--;
            case 'r' -> x++;
            case 'u' -> y--;
            case 'd' -> y++;
        }
    }

}

