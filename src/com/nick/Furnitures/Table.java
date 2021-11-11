package com.nick.Furnitures;

import java.awt.*;

public class Table extends Furniture {

    private FurnitureComponent surface;

    public Table(int x, int y, int width, int height, Color color) {
        super(x, y, width, height);

        surface = new FurnitureComponent(0,0,width,height,color,false, false);

    }

    @Override
    public ID getFurnitureID() {
        return ID.TABLE;
    }

    @Override
    public String getName() {
        return "Table";
    }

    @Override
    public FurnitureComponent[] getComponents() {
        return super.getAllComponents(new FurnitureComponent[]{surface});
    }

}