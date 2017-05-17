package com.aerohockey.mechanics.base;

/**
 * Created by sergeybutorin on 14.04.17.
 */
@SuppressWarnings({"InstanceVariableNamingConvention", "PublicField"})
public class Coords {
    public double x;
    public double y;

    public Coords() {
        this.x = 0;
        this.y = 0;
    }

    public Coords(double x, double y) {
        this.x = x;
        this.y = y;
    }
}
