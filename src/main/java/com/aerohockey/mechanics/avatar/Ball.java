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
    private double speedAbs;
    private final double radius;

    public Ball(BallCoords coords) {
        this.coords = coords;
        this.speedAbs = 3;
        this.speedX = 0;
        this.speedY = speedAbs;
        this.radius = 5;
    }

    public void move(@NotNull Platform firstPlatform, @NotNull Platform secondPlatform) {
        double newX = coords.x + speedX;
        double newY = coords.y + speedY;

        if (newY < firstPlatform.getHeight() || newY > PLAYGROUND_HEIGHT - secondPlatform.getHeight()) {
            if (firstPlatform.checkBallCollision(true, new BallCoords(newX, newY), radius)) {
                final double dSpeed;
                if (firstPlatform.getCoords().x * newX < 0) {
                    dSpeed = 4 * (firstPlatform.getCoords().x - newX) / firstPlatform.getWidth();
                } else {
                    dSpeed = 4 * (newX - firstPlatform.getCoords().x) / firstPlatform.getWidth();
                }
                speedX += dSpeed;
                checkSpeedX();
                newX = coords.x + speedX;
                speedY = sqrt(speedAbs * speedAbs - speedX * speedX);
                newY = coords.y - speedY;
            } else if (secondPlatform.checkBallCollision(false, new BallCoords(newX, newY), radius)) {
                final double dSpeed;
                if (firstPlatform.getCoords().x * newX < 0) {
                    dSpeed = 4 * (secondPlatform.getCoords().x - newX) / firstPlatform.getWidth();
                } else {
                    dSpeed = 4 * (newX - secondPlatform.getCoords().x) / secondPlatform.getWidth();
                }
                speedX += dSpeed;
                checkSpeedX();
                newX = coords.x + speedX;
                speedY = -sqrt(speedAbs * speedAbs - speedX * speedX);
                newY = coords.y - speedY;
            } else if (newY < 0 || newY > PLAYGROUND_HEIGHT) { // goal
                speedY = -speedY;
                newY = PLAYGROUND_HEIGHT / 2;
            }
        }
        if (newX > PLAYGROUND_WIDTH/2 - radius ||
                newX < -PLAYGROUND_WIDTH/2 + radius) {
            speedX = -speedX;
            newX = coords.x - speedX;
        }
        coords.x = newX;
        coords.y = newY;
    }


    private void checkSpeedX() {
        if (abs(speedX) > 0.9 * speedAbs) {
            speedX = signum(speedX) * 0.9 * speedAbs;
        }
    }

    public BallCoords getCoords() {
        return coords;
    }
}
