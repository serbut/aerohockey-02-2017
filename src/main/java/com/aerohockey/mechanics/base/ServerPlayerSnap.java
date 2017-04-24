package com.aerohockey.mechanics.base;

/**
 * Created by sergeybutorin on 16.04.17.
 */
public class ServerPlayerSnap {
    private long userId;
    private PlatformCoords platform;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long id) {
        userId = id;
    }

    public PlatformCoords getPlatform() {
        return platform;
    }

    public void setPlatform(PlatformCoords platform) {
        this.platform = platform;
    }
}
