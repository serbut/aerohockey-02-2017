package com.aerohockey.mechanics.base;

/**
 * Created by sergeybutorin on 16.05.17.
 */
@SuppressWarnings("unused")
public class BonusSnap {
    private final Coords coords;
    private final String type;

    public BonusSnap(Coords coords, String type) {
        this.coords = coords;
        this.type = type;
    }

    public Coords getCoords() {
        return coords;
    }

    public String getType() {
        return type;
    }
}
