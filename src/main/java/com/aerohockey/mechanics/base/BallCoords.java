package com.aerohockey.mechanics.base;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by sergeybutorin on 14.04.17.
 */
@SuppressWarnings("InstanceVariableNamingConvention")
public class BallCoords {
    public double x;
    public double y;

    public BallCoords(@JsonProperty("x") double x, @JsonProperty("y") double y) {
        this.x = x;
        this.y = y;
    }
}
