package com.aerohockey.mechanics.base;

/**
 * Created by sergeybutorin on 16.04.17.
 */
public class ServerPlayerSnap {
    private long userId;
    private PlatformCoords platformCoords;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long id) {
        userId = id;
    }

    public PlatformCoords getPlatformCoords() {
        return platformCoords;
    }

    public void setPlatformCoords(PlatformCoords platformCoords) {
        this.platformCoords = platformCoords;
    }
}
