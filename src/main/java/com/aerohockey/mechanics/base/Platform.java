package com.aerohockey.mechanics.base;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by sergeybutorin on 14.04.17.
 */
@SuppressWarnings("InstanceVariableNamingConvention")
public class Platform {
    public static final int PLATFORM_HEIGHT = 5;

    public Platform(@JsonProperty("x") double x) {
        this.x = x;
        //noinspection MagicNumber
        platformWidth = 50;
    }

    public double x;
    public final int platformWidth;
}
