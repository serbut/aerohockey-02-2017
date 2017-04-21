package com.aerohockey.mechanics.base;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;

import static com.aerohockey.mechanics.Config.PLAYGROUND_HEIGHT;
import static com.aerohockey.mechanics.Config.PLAYGROUND_WIDTH;

/**
 * Created by sergeybutorin on 14.04.17.
 */
@SuppressWarnings("InstanceVariableNamingConvention")
public class BallCoords {
    public BallCoords(@JsonProperty("x") double x, @JsonProperty("y") double y) {
        this.x = x;
        this.y = y;
    }

    private double x;
    private double y;
}
