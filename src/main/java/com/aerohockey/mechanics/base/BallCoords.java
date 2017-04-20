package com.aerohockey.mechanics.base;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;

/**
 * Created by sergeybutorin on 14.04.17.
 */
@SuppressWarnings("InstanceVariableNamingConvention")
public class BallCoords {
    public BallCoords(@JsonProperty("x") double x, @JsonProperty("y") double y) {
        this.x = x;
        this.y = y;
    }

    public final double x;
    public final double y;

    public @NotNull BallCoords add(@NotNull BallCoords addition) {
        return new BallCoords(x + addition.x, y + addition.y);
    }
}
