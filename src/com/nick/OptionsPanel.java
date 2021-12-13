package com.nick;

import java.awt.*;

public class OptionsPanel extends Panel {

    private Button saveButton;
    private int buttonWidth, buttonHeight, buttonX;
    private int[] buttonY;

    public OptionsPanel(int x, int y, int width, int height) {
        super(x, y, width, height);

        buttonWidth = (int) (width*0.8);
        buttonHeight = 50;
        buttonX = x+(width-buttonWidth)/2;
        int centreY = height/2;
        buttonY[1] = centreY-(buttonHeight/2);
        buttonY[0] = buttonY[1]-buttonHeight-40;
        buttonY[2] = buttonY[1]+buttonHeight+40;
        saveButton = new Button("Save", buttonX, buttonY[0], buttonWidth, buttonHeight, () -> onSaveButtonClicked());
    }

    @Override
    public void onClick(int mouseX, int mouseY) {

    }

    @Override
    public void render(Graphics g) {
        super.render(g);
        saveButton.render(g);
    }

    private void onSaveButtonClicked() {

    }

}
