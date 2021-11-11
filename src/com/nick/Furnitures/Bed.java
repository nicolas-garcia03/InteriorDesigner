package com.nick.Furnitures;

import java.awt.*;

public class Bed extends Furniture {

    private FurnitureComponent pillow, duvet;

    public Bed(int x, int y, int width, int height, Color duvetColor) {
        super(x, y, width, height);

        pillow = new FurnitureComponent(0,0,width,height*0.25,Color.WHITE,false, false);
        duvet = new FurnitureComponent(0, (int) Math.floor(pillow.getHeight()),width,height-pillow.getHeight(),duvetColor,false, false);

    }

    @Override
    public ID getFurnitureID() {
        return ID.BED;
    }

    @Override
    public String getName() {
        return "Bed";
    }

    @Override
    public FurnitureComponent[] getComponents() {
        return new FurnitureComponent[]{pillow, duvet};
    }

}