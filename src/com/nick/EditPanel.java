package com.nick;

import com.nick.Furnitures.Furniture;
import com.nick.Furnitures.ID;

import java.awt.*;

public class EditPanel extends Panel {

    private ID id;
    private String widthInput, heightInput, redInput, greenInput, blueInput;
    private Button[] buttons;
    private Button exitButton;
    private Shape[] preview;
    //TODO: CREATE BUTTON, SHOW PREVIEW

    public EditPanel(int x, int y, int width, int height) {
        super(x, y, width, height);
        exitButton = new Button("X", x + 10, y + 10, 20, 20, () -> setVisible(false));

        widthInput = "2";
        heightInput = "4";
        redInput = "255";
        greenInput = "255";
        blueInput = "255";

        Button widthMinus = new Button("-",120+mainPanel.x,60+mainPanel.y-10,10,10,() -> alterWidthInput(-1));
        Button widthPlus = new Button("+",210+mainPanel.x,60+mainPanel.y-10,10,10,() -> alterWidthInput(1));

        Button heightMinus = new Button("-",120+mainPanel.x,100+mainPanel.y-10,10,10,() -> alterHeightInput(-1));
        Button heightPlus = new Button("+",210+mainPanel.x,100+mainPanel.y-10,10,10,() -> alterHeightInput(1));

        Button redMinus = new Button("-",40+mainPanel.x,140+mainPanel.y-10,10,10,() -> alterRedInput(-5));
        Button redPlus = new Button("+",90+mainPanel.x,140+mainPanel.y-10,10,10,() -> alterRedInput(5));

        Button greenMinus = new Button("-",130+mainPanel.x,140+mainPanel.y-10,10,10,() -> alterGreenInput(-5));
        Button greenPlus = new Button("+",180+mainPanel.x,140+mainPanel.y-10,10,10,() -> alterGreenInput(5));

        Button blueMinus = new Button("-",220+mainPanel.x,140+mainPanel.y-10,10,10,() -> alterBlueInput(-5));
        Button bluePlus = new Button("+",270+mainPanel.x,140+mainPanel.y-10,10,10,() -> alterBlueInput(5));

        buttons = new Button[10];
        buttons[0] = widthMinus;
        buttons[1] = widthPlus;
        buttons[2] = heightMinus;
        buttons[3] = heightPlus;
        buttons[4] = redMinus;
        buttons[5] = redPlus;
        buttons[6] = greenMinus;
        buttons[7] = greenPlus;
        buttons[8] = blueMinus;
        buttons[9] = bluePlus;
    }

    @Override
    public void render(Graphics g) {
        super.render(g);
        exitButton.render(g);

        g.setColor(Color.BLACK);
        g.drawString("Width:", getMainPanel().x+20,getMainPanel().y+60);
        g.drawString(widthInput,getMainPanel().x+160,getMainPanel().y+60);
        g.drawString("Height:",getMainPanel().x+20,getMainPanel().y+100);
        g.drawString(heightInput,getMainPanel().x+160,getMainPanel().y+100);
        g.drawString("R:",getMainPanel().x+20,getMainPanel().y+140);
        g.drawString(redInput,getMainPanel().x+55,getMainPanel().y+140);
        g.drawString("G:",getMainPanel().x+110,getMainPanel().y+140);
        g.drawString(greenInput,getMainPanel().x+150,getMainPanel().y+140);
        g.drawString("B:",getMainPanel().x+200,getMainPanel().y+140);
        g.drawString(blueInput,getMainPanel().x+240,getMainPanel().y+140);

        for (Button b : buttons) {
            b.render(g);
        }
    }

    private void alterWidthInput(int amount) {
        if (amount < 0) {
            if (Integer.parseInt(widthInput) == 1) {
                return;
            }
        } else {
            if (Integer.parseInt(widthInput) == 10) {
                return;
            }
        }
        widthInput = Integer.toString(Integer.parseInt(widthInput)+amount);
    }

    private void alterHeightInput(int amount) {
        if (amount < 0) {
            if (Integer.parseInt(heightInput) == 1) {
                return;
            }
        } else {
            if (Integer.parseInt(heightInput) == 10) {
                return;
            }
        }
        heightInput = Integer.toString(Integer.parseInt(heightInput)+amount);
    }

    private void alterRedInput(int amount) {
        if (amount < 0) {
            if (Integer.parseInt(redInput) == 0) {
                return;
            }
        } else {
            if (Integer.parseInt(redInput) == 255) {
                return;
            }
        }
        redInput = Integer.toString(Integer.parseInt(redInput)+amount);
    }

    private void alterGreenInput(int amount) {
        if (amount < 0) {
            if (Integer.parseInt(greenInput) == 0) {
                return;
            }
        } else {
            if (Integer.parseInt(greenInput) == 255) {
                return;
            }
        }
        greenInput = Integer.toString(Integer.parseInt(greenInput)+amount);
    }

    private void alterBlueInput(int amount) {
        if (amount < 0) {
            if (Integer.parseInt(blueInput) == 0) {
                return;
            }
        } else {
            if (Integer.parseInt(blueInput) == 255) {
                return;
            }
        }
        blueInput = Integer.toString(Integer.parseInt(blueInput)+amount);
    }

    public void setID(ID id) {
        this.id = id;
        preview = Furniture.getIcon(id, mainPanel.x+mainPanel.width-100, mainPanel.y+40, 80);
        Color[] defaultColors = Furniture.getDefaultColors(id);
        int mainColorIndex = Furniture.getMainColorIndex(id);
        redInput = Integer.toString(defaultColors[mainColorIndex].getRed());
        greenInput = Integer.toString(defaultColors[mainColorIndex].getGreen());
        blueInput = Integer.toString(defaultColors[mainColorIndex].getBlue());
    }

    @Override
    public void onClick(int mouseX, int mouseY) {
        if (exitButton.getBox().contains(mouseX, mouseY)) {
            setVisible(false);
        } else {
            for (Button b : buttons) {
                if (b.getBox().contains(mouseX, mouseY)) {
                    b.onClick();
                }
            }
        }
    }

}
