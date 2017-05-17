package com.aerohockey.mechanics.base;

/**
 * Created by sergeybutorin on 16.04.17.
 */
@SuppressWarnings("unused")
public class ServerPlayerSnap {
    private long userId;
    private PlatformCoords coords;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long id) {
        userId = id;
    }

    public PlatformCoords getCoords() {
        return coords;
    }

    public void setCoords(PlatformCoords coords) {
        this.coords = coords;
    }
}
