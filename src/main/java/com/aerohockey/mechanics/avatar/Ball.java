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

    public Ball() {
        this.coords = new Coords();
        this.speedAbs = BALL_START_SPEED;
        this.speedX = 0;
        this.speedY = speedAbs;
        this.radius = BALL_RADIUS;
    }

    public Ball(double direction) {
        this.coords = new Coords();
        this.speedAbs = BALL_START_SPEED;
        this.speedX = 0;
        this.speedY = direction * speedAbs;
        this.radius = BALL_RADIUS;
    }

    public void move(@NotNull GameSession gameSession, long frameTime) {
        final double newSpeedAbs;
        if (speedAbs > BALL_NORMAL_SPEED) {
            newSpeedAbs = speedAbs + BALL_SPEED_INCREASING;
        } else {
            newSpeedAbs = speedAbs * BALL_START_ACCELERATION;
        }
        speedX *= newSpeedAbs/speedAbs;
        speedY *= newSpeedAbs/speedAbs;
        speedAbs = newSpeedAbs;

        final Coords newCoords = new Coords(coords.x + speedX * frameTime, coords.y + speedY * frameTime);

        final Platform firstPlatform = gameSession.getTop().getPlatform();
        final Platform secondPlatform = gameSession.getBottom().getPlatform();

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
        getUser(gameSession).addScore();

        gameSession.setStateChanged(true);
        if (gameSession.removeBall(this)) {
            return;
        }

        speedAbs = BALL_START_SPEED;
        speedX = 0;
        speedY = -signum(speedY) * speedAbs;
        coords.x = 0;
        coords.y = 0;
    }

    public GameUser getUser(@NotNull GameSession gameSession) {
        return speedY < 0 ? gameSession.getTop() : gameSession.getBottom();
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

    public double getSpeedY() {
        return speedY;
    }

    public BallSnap getSnap() {
        return new BallSnap(coords, radius);
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public void twoBallCollision(@NotNull Ball secondBall, long frameTime) {
        if(this.equals(secondBall)) {
            return;
        }
        if ((Math.pow((secondBall.coords.x - coords.x), 2) + Math.pow((secondBall.coords.y - coords.y), 2)) < Math.pow((secondBall.radius + radius), 2)) {
            coords.x -= speedX * frameTime;
            coords.y -= speedY * frameTime;

            secondBall.coords.x -= secondBall.speedX * frameTime;
            secondBall.coords.y -= secondBall.speedY * frameTime;

            final double newSpeedX = secondBall.speedX;
            final double newSpeedY = secondBall.speedY;

            secondBall.speedX = speedX;
            secondBall.speedY = speedY;

            speedX = newSpeedX;
            speedY = newSpeedY;
        }
    }
}
