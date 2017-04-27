package com.aerohockey.mechanics.base;

import org.jetbrains.annotations.NotNull;

/**
 * Created by sergeybutorin on 14.04.17.
 */
public class ClientSnap {
    private final @NotNull String direction;
    private long clientframeTime;

    public ClientSnap(@NotNull String direction, long clientframeTime) {
        this.direction = direction;
        this.clientframeTime = clientframeTime;
    }

    public @NotNull String getDirection() {
        return direction;
    }

    public long getClientframeTime() {
        return clientframeTime;
    }
}
