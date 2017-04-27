package com.aerohockey.mechanics.base;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;

/**
 * Created by sergeybutorin on 14.04.17.
 */
public class ClientSnap {
    private final @NotNull String direction;
    private final long frameTime;

    public ClientSnap(@NotNull @JsonProperty("button") String direction, @JsonProperty("frameTime") long frameTime) {
        this.direction = direction;
        this.frameTime = frameTime;
    }

    public @NotNull String getDirection() {
        return direction;
    }

    public long getFrameTime() {
        return frameTime;
    }
}
