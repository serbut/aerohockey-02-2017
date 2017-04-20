package com.aerohockey.mechanics.base;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;

/**
 * Created by sergeybutorin on 14.04.17.
 */
@SuppressWarnings("InstanceVariableNamingConvention")
public class PlatformCoords {
    public PlatformCoords(@JsonProperty("x") double x) {
        this.x = x;
        platformWidth = 50;
    }

    public double x;
    public int platformWidth;
}
