package com.nick;

import com.nick.Furnitures.Bed;
import com.nick.Furnitures.Furniture;
import com.nick.Furnitures.Table;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class InteriorDesigner {

    private int width, height;
    private Room room;
    private App app;

    public InteriorDesigner(int width, int height, App app) {
        this.width = width;
        this.height = height;
        this.app = app;

        FileHandler.createFolder("Rooms");

        room = new Room("Bedroom",20, 12, 0.5,3.5, width, height, false);

        room.addFurniture(new Bed(-8,1,2,4, Color.PINK));
        room.addFurniture(new Bed(-2,-5,4,8, Color.ORANGE));
        Table t = new Table(4,-2,3,4, new Color(200,100,120));
        room.addFurniture(t);
        t.rotate(3);
        room.addFurniture(new Table(4,-2,3,4, new Color(100,220,120)));

    }

    public void tick() {
        room.tick();

    }

    public void render(Graphics g) {
        room.render(g, app.getMouseX(), app.getMouseY());
    }

    public void onMouseClicked(MouseEvent e) {
        room.onMouseClicked(app.getMouseX(), app.getMouseY());
    }

    public void onKeyPressed(KeyEvent e) {
        room.onKeyPressed(e);
    }
}
