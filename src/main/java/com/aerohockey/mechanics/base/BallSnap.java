package com.aerohockey.mechanics.base;

/**
 * Created by sergeybutorin on 16.05.17.
 */
public class BallSnap extends Coords {
    private double radius;

    public BallSnap(Coords coords, double radius) {
        super(coords.x, coords.y);
        this.radius = radius;
    }

    public double getRadius() {
        return radius;
    }
}
