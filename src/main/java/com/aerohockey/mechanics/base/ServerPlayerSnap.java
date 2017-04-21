package com.aerohockey.mechanics.base;

/**
 * Created by sergeybutorin on 16.04.17.
 */
public class ServerPlayerSnap {
    private long userId;
    private Platform platform;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long id) {
        userId = id;
    }

    public Platform getPlatform() {
        return platform;
    }

    public void setPlatform(Platform platform) {
        this.platform = platform;
    }
}
