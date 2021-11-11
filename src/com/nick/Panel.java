package com.nick;

import java.awt.*;

public class Panel {

    protected Rectangle mainPanel;
    protected boolean isVisible;
    protected Button editButton;

    public Panel(int x, int y, int width, int height) {
        isVisible = false;
        mainPanel = new Rectangle(x, y, width, height);
        int editButtonWidth = mainPanel.width-60;
        editButton = new Button("Edit", mainPanel.x + (mainPanel.width-editButtonWidth)/2, mainPanel.y + mainPanel.height - 40, editButtonWidth, 20, new ClickAction() {
            @Override
            public void onClick() {
                System.out.println("Edit!");
            }
        });
    }

    public Rectangle getMainPanel() {
        return mainPanel;
    }

    public void toggleVisibility() {
        setVisible(!isVisible());
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void render(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRoundRect(mainPanel.x, mainPanel.y, mainPanel.width, mainPanel.height,10,10);
        g.setColor(Color.GRAY);
        g.drawRoundRect(mainPanel.x, mainPanel.y, mainPanel.width, mainPanel.height,10,10);
        g.setColor(new Color(200,180,180));
        g.fillRoundRect(editButton.getBox().x, editButton.getBox().y, editButton.getBox().width, editButton.getBox().height,12,12);
        g.setColor(new Color(120,80,140));
        g.drawRoundRect(editButton.getBox().x, editButton.getBox().y, editButton.getBox().width, editButton.getBox().height,12,12);
        g.drawString("Edit", editButton.getBox().x+(int)(editButton.getBox().getWidth()/3), editButton.getBox().y+15);
    }

    public void changeSides(int appWidth) {
        if (mainPanel.getX() < mainPanel.getWidth()) {
            //Move from left to right
            mainPanel.x = appWidth-mainPanel.x-mainPanel.width;
        } else {
            //Move from right to left
            mainPanel.x = appWidth-(mainPanel.x+mainPanel.width);
        }
    }

    public boolean contains(int mouseX, int mouseY) {
        return mainPanel.contains(mouseX, mouseY);
    }

    public void onClick(int mouseX, int mouseY) {
        if (editButton.getBox().contains(mouseX, mouseY)) {
            editButton.onClick();
        }
    }

}
