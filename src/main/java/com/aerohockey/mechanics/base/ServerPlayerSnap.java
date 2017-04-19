package com.aerohockey.mechanics.base;

/**
 * Created by sergeybutorin on 16.04.17.
 */
public class ServerPlayerSnap {
    private long userId;
    private byte score;

    private PlatformCoords platformCoords;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long id) {
        userId = id;
    }

    public byte getScore() {
        return score;
    }

    public void setScore(byte score) {
        this.score = score;
    }
}
