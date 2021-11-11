package com.nick;

import com.nick.Furnitures.ID;

public class Catalogue {

    public ID[] getFurnitureIDs() {
        return furnitureIDs;
    }

    private ID[] furnitureIDs;

    public Catalogue() {
        furnitureIDs = new ID[]{ID.TABLE, ID.CHAIR, ID.WARDROBE, ID.BED};
    }

}
