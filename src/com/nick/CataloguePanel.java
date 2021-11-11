package com.nick;

import java.awt.*;

public class CataloguePanel extends Panel {

    private Catalogue catalogue;

    public CataloguePanel(int appWidth, int appHeight) {
        super(20, appHeight-70, appWidth-40, 70);
        catalogue = new Catalogue();
    }

    @Override
    public void onClick(int mouseX, int mouseY) {
        System.out.println("catalogue!");
    }

    @Override
    public void render(Graphics g) {

        g.setColor(Color.WHITE);
        g.fillRoundRect(mainPanel.x, mainPanel.y, mainPanel.width, mainPanel.height,10,10);
        g.setColor(Color.GRAY);
        g.drawRoundRect(mainPanel.x, mainPanel.y, mainPanel.width, mainPanel.height,10,10);

        int previewLength = mainPanel.height-5;
        int previewsPerSlide = (mainPanel.width/previewLength)-1;
        int excessWidth = mainPanel.width-(previewsPerSlide*previewLength)-10;
        int gap = excessWidth/(previewsPerSlide);
        g.setColor(Color.DARK_GRAY);
        for (int i = 0; i < previewsPerSlide; i++) {
            g.drawRect(mainPanel.x+(previewLength*i)+((gap*i)/2)+35, mainPanel.y+5, previewLength, previewLength);
        }
    }

}
