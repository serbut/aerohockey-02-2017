package com.aerohockey.mechanics.avatar;

import com.aerohockey.mechanics.GameSession;
import com.aerohockey.mechanics.base.BallCoords;
import org.jetbrains.annotations.NotNull;

import static com.aerohockey.mechanics.Config.*;
import static java.lang.Math.signum;
import static java.lang.Math.sqrt;

/**
 * Created by sergeybutorin on 23.04.17.
 */
public class Ball {
    private BallCoords coords;
    private double speedX;
    private double speedY;
    private final double speedAbs;
    private final double radius;

    public Ball(BallCoords coords) {
        this.coords = coords;
        this.speedAbs = 0.1;
        this.speedX = 0;
        this.speedY = speedAbs;
        this.radius = 5;
    }

    public void move(@NotNull GameSession gameSession, long frameTime) {
        final Platform firstPlatform = gameSession.getTop().getPlatform();
        final Platform secondPlatform = gameSession.getBottom().getPlatform();
        final BallCoords newCoords = new BallCoords(coords.x + speedX * frameTime, coords.y + speedY * frameTime);

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
            speedY = -speedY;
            newCoords.y = 0;
        }
        coords = newCoords;
    }

    private void platformCollision(Platform platform, long frameTime, GameSession gameSession) {
        if (!platformEdgeCollision(platform, frameTime)) {
            if (Math.abs(platform.getY() - coords.y) < platform.getHeight() + radius) {
                ballInsidePlatformCollision(platform);
                goal(gameSession);
            } else {
                if (platform.getCoords().x * coords.x < 0) {
                    speedX = PLATFORM_BENDING * speedAbs * (platform.getCoords().x - coords.x) / (platform.getWidth() / 2 + radius);
                } else {
                    speedX = PLATFORM_BENDING * speedAbs * (coords.x - platform.getCoords().x) / (platform.getWidth() / 2 + radius);
                }
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
    }

    private boolean platformEdgeCollision(Platform platform, long frameTime) {
        if (Math.abs(platform.getCoords().x - coords.x + speedX * frameTime) > platform.getWidth()/2 + radius) {
            speedX = -speedX;
            coords.x += speedX * frameTime;
            coords.y += speedY * frameTime;
            return true;
        }
        return false;
    }

    private void ballInsidePlatformCollision(Platform platform) {
        if (Math.abs(platform.getCoords().x - coords.x) > platform.getWidth()/2 + radius) {
            speedY = -speedY;
            coords.y = PLAYGROUND_HEIGHT / 2;
        }
    }


    public BallCoords getCoords() {
        return coords;
    }
}
