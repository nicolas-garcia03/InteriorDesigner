package com.nick.Furnitures;

public enum ID {

    TABLE(0), CHAIR(1), WARDROBE(2), BED(3);

    private int index;

    ID(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

}
