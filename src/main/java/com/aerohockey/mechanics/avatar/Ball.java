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
        this.speedAbs = 3;
        this.speedX = 0;
        this.speedY = speedAbs;
        this.radius = 5;
    }

    public void move(@NotNull GameUser first, @NotNull GameUser second) {
        final Platform firstPlatform = first.getPlatform();
        final Platform secondPlatform = second.getPlatform();
        BallCoords newCoords = new BallCoords(coords.x + speedX, coords.y + speedY);

        if (newCoords.y < firstPlatform.getHeight() || newCoords.y > PLAYGROUND_HEIGHT - secondPlatform.getHeight()) {
            if (firstPlatform.checkBallCollision(true, newCoords, radius)) {
                newCoords = platformCollision(firstPlatform, newCoords);
            } else if (secondPlatform.checkBallCollision(false, newCoords, radius)) {
                newCoords = platformCollision(secondPlatform, newCoords);
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
        if (newCoords.x > PLAYGROUND_WIDTH/2 - radius ||
                newCoords.x < -PLAYGROUND_WIDTH/2 + radius) {
            speedX = -speedX;
            newCoords.x = coords.x - speedX;
        }
        coords = newCoords;
    }


    private void checkSpeedX() {
        final double maxAngle = 0.9;
        if (abs(speedX) > maxAngle * speedAbs) {
            speedX = signum(speedX) * maxAngle * speedAbs;
        }
    }

    private BallCoords platformCollision(Platform platform, BallCoords ballCoords) {
        final double dSpeed;
        if (platform.getCoords().x * ballCoords.x < 0) {
            dSpeed = 4 * (platform.getCoords().x - ballCoords.x) / platform.getWidth();
        } else {
            dSpeed = 4 * (ballCoords.x - platform.getCoords().x) / platform.getWidth();
        }
        speedX += dSpeed;
        checkSpeedX();
        ballCoords.x = coords.x + speedX;
        speedY = -signum(speedY) * sqrt(speedAbs * speedAbs - speedX * speedX);
        ballCoords.y = coords.y - speedY;
        return ballCoords;
    }

    public BallCoords getCoords(boolean isFirst) {
        if (!isFirst) {
            return coords;
        } else {
            return new BallCoords(-coords.x, PLAYGROUND_HEIGHT - coords.y);
        }
    }
}