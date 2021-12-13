package com.nick;

import com.nick.Furnitures.Furniture;

import java.awt.*;

public class FurnitureInfoPanel extends Panel {

    private Room room;
    private Furniture furniture;
    private Button removeFurnitureButton;

    public FurnitureInfoPanel(Room room) {
        super(room.getAppWidth()-160, 20,140,room.getAppHeight()-180);

        removeFurnitureButton = new Button("Remove", mainPanel.x + ((mainPanel.width - 100) / 2), mainPanel.y + mainPanel.height - 40, 100, 20, new ClickAction() {
            @Override
            public void onClick() {
                room.removeFurniture(furniture);
                setVisible(false);
            }
        });

        removeFurnitureButton.setExtraPositioning(-22);
    }

    @Override
    public void changeSides(int appWidth) {
        super.changeSides(appWidth);
        removeFurnitureButton.getBox().x = mainPanel.x + ((mainPanel.width - 100) / 2);
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
        removeFurnitureButton.render(g);
    }

    @Override
    public void onClick(int mouseX, int mouseY) {
        if (removeFurnitureButton.getBox().contains(mouseX, mouseY)) {
            removeFurnitureButton.onClick();
        } else {
            super.onClick(mouseX, mouseY);
        }
    }

}
