package com.aerohockey.mechanics.avatar;

import com.aerohockey.mechanics.base.BallCoords;
import org.jetbrains.annotations.NotNull;

import static com.aerohockey.mechanics.Config.*;
import static java.lang.Math.abs;
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

    public void move(@NotNull GameUser first, @NotNull GameUser second, long frameTime) {
//        frameTime = 20;
        final Platform firstPlatform = first.getPlatform();
        final Platform secondPlatform = second.getPlatform();
        final BallCoords newCoords = new BallCoords(coords.x + speedX * frameTime, coords.y + speedY * frameTime);

        if (newCoords.x > PLAYGROUND_WIDTH/2 - radius ||
                newCoords.x < -PLAYGROUND_WIDTH/2 + radius) {
            speedX = -speedX;
            coords.x += speedX * frameTime;
            coords.y += speedY * frameTime;
            return;
        }
        if (newCoords.y < firstPlatform.getHeight() + radius || newCoords.y > PLAYGROUND_HEIGHT - secondPlatform.getHeight() - radius) {
            if (firstPlatform.checkBallCollision(newCoords, radius)) {
                platformCollision(firstPlatform, newCoords, frameTime);
                return;
            } else if (secondPlatform.checkBallCollision(newCoords, radius)) {
                platformCollision(secondPlatform, newCoords, frameTime);
                return;
            } else if (newCoords.y < 0) { //goal for user2
                second.addScore();
                speedY = -speedY;
                newCoords.y = PLAYGROUND_HEIGHT / 2;
            } else if (newCoords.y > PLAYGROUND_HEIGHT) { //goal for user1
                first.addScore();
                speedY = -speedY;
                newCoords.y = PLAYGROUND_HEIGHT / 2;
            }
        }
        coords = newCoords;
    }

    private void platformCollision(Platform platform, BallCoords ballCoords, long frameTime) {
        if (coords.x < platform.getCoords().x - platform.getWidth()/2 - radius || // check platform edge collision
                coords.x > platform.getCoords().x + platform.getWidth()/2 + radius) {
            speedX = -speedX;
            coords.x += speedX * frameTime;
            coords.y += speedY * frameTime;
            return;
        }
        if (platform.getCoords().x * ballCoords.x < 0) {
            speedX = PLATFORM_BENDING * speedAbs * (platform.getCoords().x - ballCoords.x) / (platform.getWidth() / 2 + radius);
        } else {
            speedX = PLATFORM_BENDING * speedAbs * (ballCoords.x - platform.getCoords().x) / (platform.getWidth() / 2 + radius);
        }
        coords.x += speedX * frameTime;
        speedY = -signum(speedY) * sqrt(speedAbs * speedAbs - speedX * speedX);
        coords.y += speedY * frameTime;
    }

    public BallCoords getCoords(boolean isTop) {
        if (!isTop) {
            return coords;
        } else {
            return new BallCoords(-coords.x, PLAYGROUND_HEIGHT - coords.y);
        }
    }

    public double getRadius() {
        return radius;
    }
}
