package com.nick;

import com.nick.Furnitures.Furniture;

import java.awt.*;

public class FurnitureInfoPanel extends Panel {

    private Room room;
    private Furniture furniture;

    public FurnitureInfoPanel(Room room) {
        super(room.getAppWidth()-160, 20,140,room.getAppHeight()-180);
    }

    public void setFurniture(Furniture furniture) {
        this.furniture = furniture;
    }

    public Furniture getFurniture() {
        return furniture;
    }

    @Override
    public void render(Graphics g) {
        super.render(g);
        g.setColor(Color.BLACK);
        g.drawString("ID: Furniture." + furniture.getFurnitureID().toString(), mainPanel.x+10, mainPanel.y+40);
        g.drawString("X: " + furniture.getX() + " Y: " + furniture.getY(), mainPanel.x+10, mainPanel.y+80);
        g.drawString("Width: " + furniture.getWidth() + " Height: " + furniture.getHeight(), mainPanel.x+10, mainPanel.y+120);
        g.drawString("Area: " + furniture.getFloorArea(), mainPanel.x+10, mainPanel.y+160);
    }

    @Override
    public void onClick(int mouseX, int mouseY) {
        super.onClick(mouseX, mouseY);
    }

}
