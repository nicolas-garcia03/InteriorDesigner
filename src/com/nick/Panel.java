package com.nick;

import java.awt.*;

public class Panel {

    protected Rectangle mainPanel;
    protected boolean isVisible;

    public Panel(int x, int y, int width, int height) {
        isVisible = false;
        mainPanel = new Rectangle(x, y, width, height);
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

    }

}
