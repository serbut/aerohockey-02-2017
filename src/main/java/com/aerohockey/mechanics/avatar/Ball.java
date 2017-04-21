package com.aerohockey.mechanics.avatar;

import com.aerohockey.mechanics.base.BallCoords;

import static com.aerohockey.mechanics.Config.PLAYGROUND_HEIGHT;
import static com.aerohockey.mechanics.Config.PLAYGROUND_WIDTH;

/**
 * Created by sergeybutorin on 21.04.17.
 */
public class Ball {
    public static final int BALL_RADIUS = 10;

    public Ball(double x, double y) {
        this.x = x;
        this.y = y;
        this.speedY = 5;
        this.speedX = 0;
    }

    private double x;
    private double y;
    private double speedX;
    private double speedY;

    public void move() {
        double newY = y + speedY;
        double newX = x + speedX;
        if (newY >= PLAYGROUND_HEIGHT || newY <= 0) {
            speedY = -speedY;
        }
        if (newX >= PLAYGROUND_WIDTH || newX <= 0) {
            speedX = -speedX;
        }
        x = newX;
        y = newY;
    }

    public BallCoords getBallCoords() {
        return new BallCoords(x, y);
    }
}
