package com.nick.Furnitures;

import java.awt.*;

public class Wardrobe extends Furniture {

    private FurnitureComponent base, leftDoor, rightDoor;

    public Wardrobe(int x, int y, int width, int height, Color baseColor, Color doorColor) {
        super(x, y, width, height);
    }

    @Override
    public ID getFurnitureID() {
        return ID.WARDROBE;
    }

    @Override
    public String getName() {
        return "Wardrobe";
    }

    @Override
    public FurnitureComponent[] getComponents() {
        return new FurnitureComponent[]{base, leftDoor, rightDoor};
    }

}