package com.aerohockey.mechanics.base;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by sergeybutorin on 14.04.17.
 */
@SuppressWarnings("InstanceVariableNamingConvention")
public class Platform {
    public double x;

    public Platform(@JsonProperty("x") double x) {
        this.x = x;
    }
}
