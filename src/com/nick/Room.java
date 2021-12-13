package com.nick;

import com.nick.Furnitures.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Room {

    private String roomName;
    private int appWidth, appHeight, tilePixelLength, centreX, centreY, camX, camY;;
    private double tileLength, wallHeight;
    private ArrayList<Integer> tileX, tileY;
    private ArrayList<int[]> wallXY1, wallXY2;
    private ArrayList<Furniture> furniture;
    private Furniture selected, beingAdded;
    private boolean roomIsSelected;
    private Color floorColor;
    private RoomInfoPanel roomInfoPanel;
    private FurnitureInfoPanel furnitureInfoPanel;
    private CataloguePanel cataloguePanel;
    private Button confirmAdd, cancelAdd, saveButton, exitButton;
    private int initialFurnitureCount;

    public Room(String roomName, int width, int height, double tileLength, double wallHeight, int appWidth, int appHeight, boolean exists) {
        init(roomName, width, height, tileLength, wallHeight, appWidth, appHeight);
        if (exists) {
            loadFurniture("Rooms/" + roomName + "/furniture.txt");
        }
    }

    private void init(String roomName, int width, int height, double tileLength, double wallHeight, int appWidth, int appHeight) {

        this.roomName = roomName;
        this.tileLength = tileLength;
        this.wallHeight = wallHeight;
        this.appWidth = appWidth;
        this.appHeight = appHeight;

        initialFurnitureCount = 0;

        FileHandler.createFolder("Rooms/" + roomName);
        FileHandler.createNewFile("Rooms/" + roomName, "furniture.txt");

        selected = null;
        roomIsSelected = false;
        beingAdded = null;

        camX = 0;
        camY = 0;

        tilePixelLength = 20; // 0.5m = 20px

        centreX = (camX+((appWidth/2)/(tilePixelLength))); //nominal coordinate of centre X
        centreY = (camY+((appHeight/2)/(tilePixelLength))); //nominal coordinate of centre Y

        floorColor = new Color(145,130,100);

        tileX = new ArrayList<>();
        tileY = new ArrayList<>();

        for (int i = -(height/2); i < height/2; i++) {
            for (int j = -(width/2); j < width/2; j++) {
                tileX.add(j);
                tileY.add(i);
            }
        }

        wallXY1 = new ArrayList<>();
        wallXY2 = new ArrayList<>();

        wallXY1.add(new int[]{-(width/2), -(height/2)});
        wallXY2.add(new int[]{(width/2), (height/2)});

        furniture = new ArrayList<>();

        roomInfoPanel = new RoomInfoPanel(this);
        furnitureInfoPanel = new FurnitureInfoPanel(this);

        cataloguePanel = new CataloguePanel(appWidth, appHeight, this);

        confirmAdd = new Button("✓", appWidth-60,appHeight-60,30,30,this::onConfirmAdd);
        cancelAdd = new Button("x", confirmAdd.getBox().x-40,confirmAdd.getBox().y,confirmAdd.getBox().width,confirmAdd.getBox().height,this::onCancelAdd);

        saveButton = new Button("✎", 20, 20, 40, 40, this::saveRoom);
        exitButton = new Button("⌂", 20+40+20, 20, 40, 40, this::returnToHomeScreen);

    }


    private void saveRoom() {
        System.out.println("save");
        for (int i = 0; i < furniture.size(); i++) {
            Furniture f = furniture.get(i);
            StringBuilder data = new StringBuilder(f.getFurnitureID().getIndex() + "," + f.getX() + "," + f.getY() + f.getWidth() + f.getHeight() + ",");
            for (int j = 0; j < Furniture.getDefaultColors(f.getFurnitureID()).length; j++) {
                Color c = f.getComponents()[j].getColor();
                data.append(c.getRed()).append(":").append(c.getGreen()).append(":").append(c.getBlue()).append(":").append(c.getTransparency());
            }
            if (i < initialFurnitureCount-1) {
                //update using raf
                FileHandler.writeAtLine("Rooms/" + roomName + "/furniture.txt", i, data.toString());
            } else {
                //append line to the end
                FileHandler.appendLine("Rooms/" + roomName + "/furniture.txt", data.toString());
            }
        }
    }

    private void loadFurniture(String path) {
        String[] data = FileHandler.getLines(path);
        for (String line : data) {
            String[] s = line.split(",");
            String[] colorData = s[5].split(":");
            Color[] colors = new Color[colorData.length/4];
            for (int i = 0; i < colors.length; i++) {
                colors[i] = new Color(Integer.parseInt(colorData[4*i]), Integer.parseInt(colorData[4*i+1]),Integer.parseInt(colorData[4*i+2]),Integer.parseInt(colorData[4*i+3]));
            }
            Furniture f;
            switch (Integer.parseInt(s[0])) {
                case 0 -> {
                    //TABLE
                    f = new Table(Integer.parseInt(s[1]),Integer.parseInt(s[2]),Integer.parseInt(s[3]),Integer.parseInt(s[4]),colors[0]);
                }
                case 1 -> {
                    //CHAIR
                    f = new Chair(Integer.parseInt(s[1]),Integer.parseInt(s[2]),Integer.parseInt(s[3]),Integer.parseInt(s[4]),colors[0]);
                }
                case 2 -> {
                    //WARDROBE
                    f = new Wardrobe(Integer.parseInt(s[1]),Integer.parseInt(s[2]),Integer.parseInt(s[3]),Integer.parseInt(s[4]),colors[0],colors[1]);
                }
                case 3 -> {
                    //BED
                    f = new Bed(Integer.parseInt(s[1]),Integer.parseInt(s[2]),Integer.parseInt(s[3]),Integer.parseInt(s[4]),colors[0]);
                }
                default -> {
                    continue;
                }
            }
            furniture.add(f);
        }
    }

    private void returnToHomeScreen() {

    }

    public String getRoomName() {
        return roomName;
    }

    public int getAppWidth() {
        return appWidth;
    }

    public int getAppHeight() {
        return appHeight;
    }

    public void addFurniture(Furniture f) {
        if (validPosition(f.getX(), f.getY(), f.getWidth(), f.getHeight())) {
            furniture.add(f);
        }
    }

    public void removeFurniture(Furniture f) {
        furniture.remove(f);
    }

    public ArrayList<Furniture> getFurniture() {
        return furniture;
    }

    public void previewNewFurniture(ID id, int width, int height, Color color) {
        switch (id) {
            case CHAIR -> beingAdded = new Table(0,0,width,height,color);
            case BED -> beingAdded = new Bed(0,0,width,height,color);
        }
    }

    private void onConfirmAdd() {
        if (!intersectsFurniture(beingAdded.getX(), beingAdded.getY(), beingAdded.getWidth(), beingAdded.getHeight())) {
            addFurniture(beingAdded);
            beingAdded = null;
        }
    }

    private void onCancelAdd() {
        beingAdded = null;
    }

    public boolean validPosition(int x, int y, int width, int height) {
        return (!intersectsFurniture(x, y, width, height) && !intersectsWalls(x, y, width, height));
    }

    private boolean intersectsFurniture(int x, int y, int width, int height) {
        for (Furniture f1 : furniture) {
            if (f1 == selected) {
                continue;
            }
            if (f1.intersects(x, y, width, height)) {
                return true;
            }
        }
        return false;
    }

    private boolean intersectsWalls(int x, int y, int width, int height) {
        boolean topLeft = false;
        boolean bottomRight = false;
        for (int i = 0; i < tileX.size(); i++) {
            if (x == tileX.get(i) && y == tileY.get(i)) {
                topLeft = true;
            }
            if (x+width-1 == tileX.get(i) && y+height-1 == tileY.get(i)) {
                bottomRight = true;
            }
        }
        return !(topLeft && bottomRight);
    }

    public double getTotalFloorArea() {
        return tileX.size()*tileLength;
    }

    public double getOccupiedFloorArea() {
        int total = 0;
        for (Furniture f : furniture) {
            total += f.getFloorArea();
        }
        return total;
    }

    public double getUnoccupiedFloorArea() {
        return (getTotalFloorArea() - getOccupiedFloorArea());
    }

    public void tick() {

    }

    public void render(Graphics g, int mouseX, int mouseY) {

        // ***** - Draw the grass background - *****
        g.setColor(new Color(140,180,100));
        g.fillRect(0,0,appWidth,appHeight);

        // ***** - Draw the floor of the room - *****
        g.setColor(floorColor);
        for (int i = 0; i < tileX.size(); i++) {
            int[] values = tileToPixel(tileX.get(i), tileY.get(i),tileLength,tileLength);
            g.fillRect(values[0], values[1], tilePixelLength, tilePixelLength);
        }

        // ***** - Draw the furniture - *****
        for (Furniture f : furniture) {
            for (FurnitureComponent c : f.getComponents()) {
                int[] values = tileToPixel(f.getX() + c.getXOffset(), f.getY() + c.getYOffset(), c.getWidth(), c.getHeight());
                g.setColor(c.getColor());
                if (c.isOutline()) {
                    g.drawRect(values[0], values[1], values[2], values[3]);
                } else {
                    g.fillRect(values[0], values[1], values[2], values[3]);
                }

                //Draw over a highlight if selected
                if (f == selected) {
                    g.setColor(new Color(80, 60, 180, 80));
                    g.fillRect(values[0], values[1], values[2], values[3]);
                }
            }
            g.setColor(Color.WHITE);
        }

        if (beingAdded != null) {
            for (FurnitureComponent c : beingAdded.getComponents()) {
                int[] values = tileToPixel(beingAdded.getX() + c.getXOffset(), beingAdded.getY() + c.getYOffset(), c.getWidth(), c.getHeight());
                g.setColor(c.getColor());
                if (c.isOutline()) {
                    g.drawRect(values[0], values[1], values[2], values[3]);
                } else {
                    g.fillRect(values[0], values[1], values[2], values[3]);
                }
                if (intersectsFurniture(beingAdded.getX(), beingAdded.getY(), beingAdded.getWidth(), beingAdded.getHeight())) {
                    g.setColor(new Color(255, 0, 0, 80));
                    g.fillRect(values[0], values[1], values[2], values[3]);
                    g.setColor(new Color(200,200,200,50));
                    g.fillRect(confirmAdd.getBox().x,confirmAdd.getBox().y,confirmAdd.getBox().width,confirmAdd.getBox().height);
                } else {
                    g.setColor(new Color(0, 255, 0, 100));
                    g.fillRect(values[0], values[1], values[2], values[3]);
                }
            }
            confirmAdd.render(g);
            cancelAdd.render(g);
        }

        // ***** - Highlight the hovered tile - *****
        g.setColor(new Color(255,255,255,60));
        int[] tileMouseCoordinates = pixelToTile(mouseX, mouseY);
        int[] newMousePos = tileToPixel(tileMouseCoordinates[0], tileMouseCoordinates[1],0,0);
        g.fillRect(newMousePos[0], newMousePos[1], tilePixelLength, tilePixelLength);

        // ***** - Mark the centre - *****
        g.setColor(Color.BLACK);
        g.fillRect(centreX*tilePixelLength,centreY*tilePixelLength,1,1);

        // ***** - Show the room info panel if visible - *****

        if (roomInfoPanel.isVisible()) {
            roomInfoPanel.render(g);
        }

        // ***** - Show furniture info panel if visible - *****

        if (furnitureInfoPanel.isVisible()) {
            furnitureInfoPanel.render(g);
        }

        // ***** - Show catalogue panel if visible - *****

        if (cataloguePanel.isVisible()) {
            cataloguePanel.render(g);
        }

        cataloguePanel.renderButton(g);

        // ***** - Render HUD Buttons - *****

        saveButton.render(g);
        exitButton.render(g);

    }

    private int[] tileToPixel(int x, int y, double width, double height) {
        int newX = (x*tilePixelLength);
        int newY = (y*tilePixelLength);
        int newWidth = (int) Math.ceil(width*tilePixelLength);
        int newHeight = (int) Math.ceil(height*tilePixelLength);
        newX += camX+(appWidth/2);
        newY += camY+(appHeight/2);
        return new int[]{newX, newY, newWidth, newHeight};
    }

    public int[] pixelToTile(int x, int y) {
        int newX = -(camX+(appWidth/2));
        int newY = -(camY+(appHeight/2));
        newX /= tilePixelLength;
        newY /=tilePixelLength;
        newX += x/tilePixelLength;
        newY += y/tilePixelLength;
        return new int[]{newX, newY};
    }

    public void onMouseClicked(int mouseX, int mouseY) {

        int[] newCoordinates = pixelToTile(mouseX, mouseY);

        if (saveButton.getBox().contains(mouseX, mouseY)) {
            saveButton.onClick();
            return;
        } else if (exitButton.getBox().contains(mouseX, mouseY)) {
            exitButton.onClick();
            return;
        }

        if (beingAdded != null) {
            if (confirmAdd.getBox().contains(mouseX, mouseY)) {
                confirmAdd.onClick();
            } else if (cancelAdd.getBox().contains(mouseX, mouseY)) {
                cancelAdd.onClick();
            }
            return;
        }

        boolean roomPreviouslySelected = roomIsSelected;
        roomIsSelected = false;

        Furniture previouslySelected = selected;
        selected = null;

        boolean fipPreviouslyVisible = furnitureInfoPanel.isVisible();

        boolean catalogueInteracted = false;

        //Is room info panel clicked on?
        if (roomInfoPanel.contains(mouseX, mouseY)) {
            if (roomInfoPanel.isVisible()) {
                roomInfoPanel.onClick(mouseX, mouseY);
                roomIsSelected = true;
                return;
            }
        } else {
            roomInfoPanel.setVisible(false);
        }

        //Is furniture info panel clicked on?
        if (furnitureInfoPanel.contains(mouseX, mouseY)) {
            if (furnitureInfoPanel.isVisible()) {
                furnitureInfoPanel.onClick(mouseX, mouseY);
                selected = furnitureInfoPanel.getFurniture();
                return;
            }
        } else {
            furnitureInfoPanel.setVisible(false);
        }

        if (cataloguePanel.isVisible()) {
            if (cataloguePanel.contains(mouseX, mouseY)) {
                cataloguePanel.onClick(mouseX, mouseY);
                catalogueInteracted = true;
            } else if (cataloguePanel.innerPanelIsClickedOn(mouseX, mouseY)) {
                cataloguePanel.innerPanelOnClick(mouseX, mouseY);
                return;
            }
        }

        if (cataloguePanel.openCloseButtonClicked(mouseX, mouseY)) {
            cataloguePanel.onButtonClicked();
            return;
        }

        if (!roomPreviouslySelected) {
            //Is furniture clicked on?
            for (Furniture f : furniture) {
                if (f.contains(newCoordinates[0], newCoordinates[1])) {
                    selected = f;
                }
            }
            if (selected == previouslySelected && selected != null) {
                selected = null;
                furnitureInfoPanel.setVisible(false);
            }
        }

        if (selected == null && previouslySelected != null) {
            roomIsSelected = false;
            return;
        }

        if (previouslySelected == null && selected != null) {
            furnitureInfoPanel.setFurniture(selected);
            furnitureInfoPanel.setVisible(true);
        }

        if (selected == previouslySelected && selected != null) {
            furnitureInfoPanel.toggleVisibility();
        }

        if (selected == null) {
            furnitureInfoPanel.setVisible(false);
        }

        if (selected != previouslySelected && selected != null) {
            furnitureInfoPanel.setFurniture(selected);
            furnitureInfoPanel.setVisible(true);
        }

        if(roomPreviouslySelected && !furnitureInfoPanel.isVisible()) {
            roomIsSelected = false;
        //Is floor/room clicked on?
        } else if (selected == null && previouslySelected == null && !catalogueInteracted) {
            for (int i = 0; i < tileX.size(); i++) {
                if (newCoordinates[0] == tileX.get(i) && newCoordinates[1] == tileY.get(i)) {
                    roomIsSelected = true;
                    roomInfoPanel.setVisible(true);
                }
            }
        }

    }

    public void onKeyPressed(KeyEvent e) {
        if (this.selected != null) {
            moveSelected(e);
        } else if (beingAdded != null) {
            moveBeingAdded(e);
        }

    }

    private void moveSelected(KeyEvent e) {
        switch (e.getKeyCode()) {
            case 37 -> {
                //Move left
                if (validPosition(selected.getX()-1, selected.getY(), selected.getWidth(), selected.getHeight())) {
                    selected.move('l');
                }
            }
            case 38 -> {
                //Move up
                if (validPosition(selected.getX(), selected.getY()-1, selected.getWidth(), selected.getHeight())) {
                    selected.move('u');
                }
            }
            case 39 -> {
                //Move right
                if (validPosition(selected.getX()+1, selected.getY(), selected.getWidth(), selected.getHeight())) {
                    selected.move('r');
                }
            }
            case 40 -> {
                //Move down
                if (validPosition(selected.getX(), selected.getY()+1, selected.getWidth(), selected.getHeight())) {
                    selected.move('d');
                }
            }
        }

        int[] values = tileToPixel(selected.getX(),selected.getY(),selected.getWidth(),selected.getHeight());
        if (furnitureInfoPanel.getMainPanel().intersects(new Rectangle(values[0],values[1],values[2],values[3]))) {
            furnitureInfoPanel.changeSides(appWidth);
        }
        if (roomInfoPanel.getMainPanel().intersects(new Rectangle(values[0],values[1],values[2],values[3]))) {
            roomInfoPanel.changeSides(appWidth);
        }
    }

    private void moveBeingAdded(KeyEvent e) {
        switch (e.getKeyCode()) {
            case 37 -> {
                //Move left
                beingAdded.move('l');
            }
            case 38 -> {
                //Move up
                beingAdded.move('u');
            }
            case 39 -> {
                //Move right
                beingAdded.move('r');
            }
            case 40 -> {
                //Move down
                beingAdded.move('d');
            }
        }

        int[] values = tileToPixel(beingAdded.getX(),beingAdded.getY(),beingAdded.getWidth(),beingAdded.getHeight());
        if (furnitureInfoPanel.getMainPanel().intersects(new Rectangle(values[0],values[1],values[2],values[3]))) {
            furnitureInfoPanel.changeSides(appWidth);
        }
        if (roomInfoPanel.getMainPanel().intersects(new Rectangle(values[0],values[1],values[2],values[3]))) {
            roomInfoPanel.changeSides(appWidth);
        }
    }

}
