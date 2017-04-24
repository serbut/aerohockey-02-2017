package com.aerohockey.mechanics.base;

import org.jetbrains.annotations.NotNull;

/**
 * Created by sergeybutorin on 14.04.17.
 */
public class ClientSnap {
    private final @NotNull String direction;

    public ClientSnap(@NotNull String direction) {
        this.direction = direction;
    }

    public @NotNull String getDirection() {
        return direction;
    }
}
