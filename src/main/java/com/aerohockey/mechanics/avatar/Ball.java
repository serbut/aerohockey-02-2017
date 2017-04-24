package com.aerohockey.mechanics.avatar;

import com.aerohockey.mechanics.base.BallCoords;
import com.aerohockey.mechanics.base.PlatformCoords;
import org.jetbrains.annotations.NotNull;

import static com.aerohockey.mechanics.Config.*;

/**
 * Created by sergeybutorin on 23.04.17.
 */
public class Ball {
    private BallCoords coords;
    private double speedX;
    private double speedY;
    private double radius;

    public Ball(BallCoords coords) {
        this.coords = coords;
        this.speedX = 0;
        this.speedY = 3;
        this.radius = 5;
    }

    public void move(@NotNull Platform firstPlatform, @NotNull Platform secondPlatform) {
        double newX = coords.x + speedX;
        double newY = coords.y + speedY;

        if (newX > PLAYGROUND_WIDTH/2 - radius ||
                newX < -PLAYGROUND_WIDTH/2 + radius) {
            speedX = -speedX;
            newX = coords.x - speedX;
        }
        if (newY < firstPlatform.getHeight() || newY > PLAYGROUND_HEIGHT - secondPlatform.getHeight()) {
            if (firstPlatform.checkBallCollision(true, new BallCoords(newX, newY))) {
                final double dSpeed = 4 * (newX - firstPlatform.getCoords().x)/firstPlatform.getWidth();
                speedX += dSpeed;
                newX = coords.x + speedX;
                speedY = dSpeed - speedY;
                newY = coords.y - speedY;
            } else if (secondPlatform.checkBallCollision(false, new BallCoords(newX, newY))) {
                final double dSpeed = 4 * (newX - secondPlatform.getCoords().x)/secondPlatform.getWidth();
                speedX += dSpeed;
                newX = coords.x + speedX;
                speedY = dSpeed - speedY;
                newY = coords.y - speedY;
            } else if (newY < 0 || newY > PLAYGROUND_HEIGHT) { // goal
                speedY = -speedY;
                newY = PLAYGROUND_HEIGHT / 2;
            }
        }
        coords.x = newX;
        coords.y = newY;
    }

    public BallCoords getCoords() {
        return coords;
    }

    public void setCoords(BallCoords coords) {
        this.coords = coords;
    }
}
