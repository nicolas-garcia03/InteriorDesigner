package com.nick;

import java.awt.*;

public class RoomInfoPanel extends Panel {

    private Room room;

    public RoomInfoPanel(Room room) {
        super(20,20,160,room.getAppHeight()-180);
        this.room = room;
    }

    @Override
    public void render(Graphics g) {
        super.render(g);
        g.setColor(Color.BLACK);
        g.drawString( "Name: " + room.getRoomName(), mainPanel.x+10, mainPanel.y+40);
        g.drawString( "Total Area: " + (int) room.getTotalFloorArea(), mainPanel.x+10, mainPanel.y+80);
        g.drawString( "Unoccupied Area: " + (int) room.getUnoccupiedFloorArea(), mainPanel.x+10, mainPanel.y+120);
        g.drawString( "Occupied Area: " + (int) room.getOccupiedFloorArea(), mainPanel.x+10, mainPanel.y+160);
    }

    @Override
    public void onClick(int mouseX, int mouseY) {
        super.onClick(mouseX, mouseY);
    }

}
