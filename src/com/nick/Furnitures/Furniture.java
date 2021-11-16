package com.nick.Furnitures;

import java.awt.*;
import java.awt.geom.Ellipse2D;

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

    public static Color[] getDefaultColors(ID id) {
        switch (id) {
            case CHAIR -> {
                return new Color[]{new Color(160,140,110)};
            }
            case TABLE -> {
                return new Color[]{new Color(220,190,130)};
            }
            case WARDROBE -> {
                return new Color[]{new Color(120,100,80),new Color(140,120,100),new Color(140,120,100)};
            }
            case BED -> {
                return new Color[]{new Color(240,240,240), new Color(200,20,20)};
            }
            default ->  {
                return new Color[]{Color.BLACK};
            }
        }
    }

    public static int getMainColorIndex(ID id) {
        switch (id) {
            case BED -> {
                return 1;
            }
            default ->  {
                return 0;
            }
        }
    }

    public static Shape[] getIcon(ID id, int x, int y, int boxLength) {
        switch (id) {
            case CHAIR -> {
                return new Rectangle[]{(new Rectangle(x+20,y+20,boxLength-40,boxLength-40))};
            }
            case TABLE -> {
                return new Ellipse2D[]{(new Ellipse2D.Float(x+10,y+10,boxLength-20,boxLength-20))};
            }
            case WARDROBE -> {
                int width = boxLength/2;
                Rectangle outer = new Rectangle(x+(boxLength-width)/2,y,width,boxLength-4);
                Rectangle left = new Rectangle(x+5+(boxLength-width)/2,y+2,(width/2)-2,boxLength-6);
                Rectangle right = new Rectangle(x+left.x+5+(boxLength-width)/2,y+2,(width/2)-2,boxLength-6);
                return new Rectangle[]{outer,left,right};
            }
            case BED -> {
                int width = boxLength/2;
                boxLength-=8;
                Rectangle pillow = new Rectangle(x+((boxLength+8-width)/2),y+8,width,boxLength/4);
                Rectangle duvet = new Rectangle(x+((boxLength+8-width)/2),(int)(pillow.getY()+pillow.getHeight()),width,(int)(boxLength-pillow.getHeight()-6));
                return new Rectangle[]{pillow,duvet};
            }
            default ->  {
                return null;
            }
        }
    }

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

