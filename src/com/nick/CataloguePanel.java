package com.nick;

import com.nick.Furnitures.Furniture;
import com.nick.Furnitures.ID;

import java.awt.*;

public class CataloguePanel extends Panel {

    private ID[] catalogue;

    private int previewOffset;
    private int previewLength;
    private int previewsPerSlide;
    private int[] previewsX;
    private Shape[][] icons;
    private Button openCloseButton;
    private EditPanel editPanel;
    private int appWidth, appHeight;
    private Room room;

    public CataloguePanel(int appWidth, int appHeight, Room room) {
        super(20, appHeight-70, appWidth-40, 70);
        this.appWidth = appWidth;
        this.appHeight = appHeight;
        this.room = room;

        openCloseButton = new Button("▲", (appWidth/2)-30,appHeight-20,60,20, this::toggleVisibility);

        catalogue = new ID[2];
        catalogue[0] = ID.CHAIR;
        catalogue[1] = ID.BED;

        previewLength = mainPanel.height-previewOffset;
        previewsPerSlide = (mainPanel.width/previewLength)-1;

        previewsX = new int[previewsPerSlide];

        for (int i = 0; i < previewsPerSlide; i++) {
            int xOffset = (previewLength*i)+previewOffset;
            previewsX[i] = mainPanel.x+xOffset;
        }

        icons = new Shape[catalogue.length][3];

        for (int i = 0; i < catalogue.length; i++) {
            icons[i] = Furniture.getIcon(catalogue[i],previewsX[i], mainPanel.y+previewOffset,previewLength);
        }

        int fsPanelWidth = appWidth/2;
        int fsPanelHeight = appHeight/2;

        int fsPanelX = (appWidth-fsPanelWidth)/2;
        int fsPanelY = (appHeight-fsPanelHeight)/2;

        editPanel = new EditPanel(fsPanelX,fsPanelY,fsPanelWidth,fsPanelHeight, this::createFurniture);

    }

    public void createFurniture() {
        editPanel.setVisible(false);
        onButtonClicked();
        room.previewNewFurniture(editPanel.getID(), editPanel.getFWidth(), editPanel.getFHeight(), new Color(editPanel.getR(), editPanel.getG(), editPanel.getB()));
    }

    public boolean innerPanelIsClickedOn(int mouseX, int mouseY) {
        return (editPanel.contains(mouseX, mouseY));
    }

    @Override
    public void onClick(int mouseX, int mouseY) {
        if (mouseY > mainPanel.getY()+previewOffset && mouseY < mainPanel.getY()+mainPanel.getWidth()-previewOffset) {
            for (int i = 0; i < catalogue.length; i++) {
                if (mouseX > previewsX[i] && mouseX < previewsX[i]+previewLength) {
                    editPanel.setID(catalogue[i]);
                    editPanel.setVisible(true);
                }
            }
        }
    }

    public boolean openCloseButtonClicked(int mouseX, int mouseY) {
        return openCloseButton.getBox().contains(mouseX, mouseY);
    }

    public void onButtonClicked() {
        if (!isVisible) {
            openCloseButton.setText("▼");
            openCloseButton.getBox().y = getMainPanel().y-openCloseButton.getBox().height;
            isVisible = true;
        } else {
            openCloseButton.setText("▲");
            openCloseButton.getBox().y = appHeight - openCloseButton.getBox().height;
            isVisible = false;
        }
    }

    public void innerPanelOnClick(int mouseX, int mouseY) {
        editPanel.onClick(mouseX,mouseY);
    }

    @Override
    public void render(Graphics g) {
        super.render(g);
        for (int i = 0; i < previewsX.length; i++) {
            g.setColor(Color.DARK_GRAY);
            g.drawRect(previewsX[i], mainPanel.y, previewLength,previewLength);
            if (i < catalogue.length) {
                Color[] defaultColors = Furniture.getDefaultColors(catalogue[i]);
                for (int j = 0; j < icons[i].length; j++) {
                    if (icons[j] != null) {
                        Shape s = icons[i][j];
                        g.setColor(defaultColors[j]);
                        if (s.getClass() == Rectangle.class) {
                            g.fillRect(((Rectangle) s).x,((Rectangle) s).y,((Rectangle) s).width,((Rectangle) s).height);
                        }
                    }
                }
            }
        }

        openCloseButton.render(g);

        if (editPanel.isVisible()) {
            editPanel.render(g);
        }

    }

    public void renderButton(Graphics g) {
        openCloseButton.render(g);
    }

}