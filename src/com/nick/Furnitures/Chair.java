package com.nick.Furnitures;

import java.awt.*;

public class Chair extends Furniture {

    private FurnitureComponent surface;

    public Chair(int x, int y, int width, int height, Color color) {
        super(x, y, width, height);

        surface = new FurnitureComponent(0,0,width,height,color,false, false);

    }

    @Override
    public ID getFurnitureID() {
        return ID.CHAIR;
    }

    @Override
    public String getName() {
        return "Chair";
    }

    @Override
    public FurnitureComponent[] getComponents() {
        return super.getAllComponents(new FurnitureComponent[]{surface});
    }

}