package com.aerohockey.mechanics.avatar;

import com.aerohockey.mechanics.GameSession;
import com.aerohockey.mechanics.base.BallSnap;
import com.aerohockey.mechanics.base.Coords;
import org.jetbrains.annotations.NotNull;

import static com.aerohockey.mechanics.Config.*;
import static java.lang.Math.signum;
import static java.lang.Math.sqrt;

/**
 * Created by sergeybutorin on 23.04.17.
 */
public class Ball {
    private Coords coords;
    private double speedX;
    private double speedY;
    private double speedAbs;
    private double radius;

    public Ball(@NotNull Coords coords) {
        this.coords = coords;
        this.speedAbs = BALL_START_SPEED;
        this.speedX = 0;
        this.speedY = speedAbs;
        this.radius = BALL_RADIUS;
    }

    public void move(@NotNull GameSession gameSession, long frameTime) {
        speedAbs += BALL_SPEED_INCREASING;
        final Platform firstPlatform = gameSession.getTop().getPlatform();
        final Platform secondPlatform = gameSession.getBottom().getPlatform();
        final Coords newCoords = new Coords(coords.x + speedX * frameTime, coords.y + speedY * frameTime);

        if (Math.abs(newCoords.x) > PLAYGROUND_WIDTH / 2 - radius) {
            speedX = -speedX;
            coords.x += speedX * frameTime;
            coords.y += speedY * frameTime;
            return;
        }

        if (firstPlatform.checkBallCollision(newCoords, radius)) {
            platformCollision(firstPlatform, frameTime, gameSession);
            return;
        } else if (secondPlatform.checkBallCollision(newCoords, radius)) {
            platformCollision(secondPlatform, frameTime, gameSession);
            return;
        } else if (Math.abs(newCoords.y) > PLAYGROUND_HEIGHT/2) {
            goal(gameSession);
            return;
        }
        coords = newCoords;
    }

    private void platformCollision(@NotNull Platform platform, long frameTime, @NotNull GameSession gameSession) {
        if (!platformEdgeCollision(platform, frameTime)) {
            if (Math.abs(platform.getY() - coords.y) < platform.getHeight() + radius) {
                goal(gameSession);
            } else {
                speedX = PLATFORM_BENDING * speedAbs * (coords.x - platform.getCoords().x) / (platform.getWidth() / 2 + radius);
                coords.x += speedX * frameTime;
                speedY = -signum(speedY) * sqrt(speedAbs * speedAbs - speedX * speedX);
                coords.y += speedY * frameTime;
            }
        }
    }

    private void goal(@NotNull GameSession gameSession) {
        if (coords.y > 0) {
            gameSession.getBottom().addScore();
        } else {
            gameSession.getTop().addScore();
        }

        gameSession.setStateChanged(true);

        speedAbs = BALL_START_SPEED;
        speedX = 0;
        speedY = -signum(speedY) * speedAbs;
        coords.x = 0;
        coords.y = 0;
    }

    private boolean platformEdgeCollision(@NotNull Platform platform, long frameTime) {
        if (Math.abs(platform.getCoords().x - coords.x + speedX * frameTime) > platform.getWidth()/2 + radius) {
            speedX = -speedX;
            coords.x += speedX * frameTime;
            coords.y += speedY * frameTime;
            return true;
        }
        return false;
    }

    public double getRadius() {
        return radius;
    }

    public Coords getCoords() {
        return coords;
    }

    public BallSnap getSnap() {
        return new BallSnap(coords, radius);
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }
}
