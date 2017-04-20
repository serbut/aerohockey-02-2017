package com.aerohockey.mechanics.base;

import org.jetbrains.annotations.NotNull;

/**
 * Created by sergeybutorin on 14.04.17.
 */
public class ClientSnap {
    private @NotNull String direction;

    ClientSnap(@NotNull String direction) {
        this.direction = direction;
    }

    public @NotNull String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

//    private @NotNull PlatformCoords platformCoords;
//    private long frameTime;
//
//    public @NotNull PlatformCoords getPlatformCoords() {
//        return platformCoords;
//    }
//
//    public void setPlatformCoords(PlatformCoords platformCoords) {
//        this.platformCoords = platformCoords;
//    }
//
//    public long getFrameTime() {
//        return frameTime;
//    }
//
//    public void setFrameTime(long frameTime) {
//        this.frameTime = frameTime;
//    }
}
