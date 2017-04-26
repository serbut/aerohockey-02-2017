package com.aerohockey.mechanics.base;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by sergeybutorin on 14.04.17.
 */
@SuppressWarnings({"InstanceVariableNamingConvention", "PublicField"})
public class PlatformCoords {
    public double x;

    public PlatformCoords(@JsonProperty("x") double x) {
        this.x = x;
    }
}
