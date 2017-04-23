package com.aerohockey.mechanics.avatar;

import com.aerohockey.mechanics.base.BallCoords;
import org.jetbrains.annotations.NotNull;

import static com.aerohockey.mechanics.Config.BALL_RADIUS;
import static com.aerohockey.mechanics.Config.PLAYGROUND_HEIGHT;
import static com.aerohockey.mechanics.Config.PLAYGROUND_WIDTH;

/**
 * Created by sergeybutorin on 23.04.17.
 */
public class Ball {
    private BallCoords coords;
    private double speedX;
    private double speedY;

    public Ball(BallCoords coords) {
        this.coords = coords;
        this.speedX = 3;
        this.speedY = 3;
    }

    public void move() {
        if (coords.x + speedX > PLAYGROUND_WIDTH/2 - BALL_RADIUS ||
                coords.x + speedX < -PLAYGROUND_WIDTH/2 + BALL_RADIUS) {
            speedX = -speedX;
        }
        if (coords.y + speedY > PLAYGROUND_HEIGHT - BALL_RADIUS ||
                coords.y + speedY < BALL_RADIUS) {
            speedY = -speedY;
        }
        coords.x += speedX;
        coords.y += speedY;
    }

    public void setSpeedX(double speedX) {
        this.speedX = speedX;
    }

    public void setSpeedY(double speedY) {
        this.speedY = speedY;
    }

    public double getSpeedX() {
        return speedX;
    }

    public double getSpeedY() {
        return speedY;
    }

    public BallCoords getCoords() {
        return coords;
    }

    public void setCoords(BallCoords coords) {
        this.coords = coords;
    }
}
