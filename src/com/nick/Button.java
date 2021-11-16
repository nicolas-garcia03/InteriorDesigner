package com.nick;

import java.awt.*;

public class Button {

    private String text;
    private Rectangle box;
    private ClickAction clickAction;

    public Button(String text, int x, int y, int width, int height, ClickAction clickAction) {
        box = new Rectangle(x,y,width,height);
        this.text = text;
        this.clickAction = clickAction;
    }

    public void render(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(box.x, box.y, box.width, box.height);
        g.setColor(Color.GRAY);
        g.drawRect(box.x, box.y, box.width, box.height);
        g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));
        g.drawString(text, box.x+(box.width/2)-5, box.y+(box.height/2)+6);
    }

    public Rectangle getBox() {
        return box;
    }

    public void onClick() {
        clickAction.onClick();
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setY(int y) {
        box.y = y;
    }

}

interface ClickAction {

    void onClick();

}
