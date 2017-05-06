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
//        frameTime = 20;
        final Platform firstPlatform = gameSession.getTop().getPlatform();
        final Platform secondPlatform = gameSession.getBottom().getPlatform();
        final BallCoords newCoords = new BallCoords(coords.x + speedX * frameTime, coords.y + speedY * frameTime);

        if (newCoords.x > PLAYGROUND_WIDTH / 2 - radius ||
                newCoords.x < -PLAYGROUND_WIDTH / 2 + radius) {
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
        } else if (newCoords.y < 0 || newCoords.y > PLAYGROUND_HEIGHT) {
            goal(gameSession);
            speedY = -speedY;
            newCoords.y = PLAYGROUND_HEIGHT / 2;
        }
        coords = newCoords;
    }

    private void platformCollision(Platform platform, long frameTime, GameSession gameSession) {
        if (platformEdgeCollision(platform, frameTime) &&
                Math.abs(platform.getY() - (coords.y + speedY * frameTime)) < radius) {
            ballInsidePlatformCollision(platform);
//            goal(gameSession);
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

    private void goal(@NotNull GameSession gameSession) {
        if (coords.y < PLAYGROUND_HEIGHT/2) {
            gameSession.getBottom().addScore();
        } else {
            gameSession.getTop().addScore();
        }
    }

    private boolean platformEdgeCollision(Platform platform, long frameTime) {
        if (coords.x < platform.getCoords().x - platform.getWidth() / 2 - radius + PLATFORM_SPEED * frameTime ||
                coords.x > platform.getCoords().x + platform.getWidth() / 2 + radius - PLATFORM_SPEED * frameTime) {
            speedX = -speedX;
            coords.x += speedX * frameTime;
            coords.y += speedY * frameTime;
            return true;
        }
        return false;
    }

    private void ballInsidePlatformCollision(Platform platform) {
        if (coords.x > platform.getCoords().x - platform.getWidth() / 2 - radius ||
                coords.x < platform.getCoords().x + platform.getWidth() / 2 - radius) {
            speedY = -speedY;
            coords.y = PLAYGROUND_HEIGHT / 2;
        }
    }

    public BallCoords getCoords(boolean isTop) {
        if (!isTop) {
            return coords;
        } else {
            return new BallCoords(-coords.x, PLAYGROUND_HEIGHT - coords.y);
        }
    }
}
