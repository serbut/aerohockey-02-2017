package com.aerohockey.mechanics.avatar;

import com.aerohockey.mechanics.base.BallCoords;
import com.aerohockey.mechanics.base.PlatformCoords;
import org.jetbrains.annotations.NotNull;

import static com.aerohockey.mechanics.Config.PLAYGROUND_HEIGHT;
import static com.aerohockey.mechanics.Config.PLAYGROUND_WIDTH;

/**
 * Created by sergeybutorin on 23.04.17.
 */
public class Platform {
    private final PlatformCoords coords;

    private final double width;
    private final double height;
    private final boolean isTop;

    public Platform(@NotNull PlatformCoords coords, boolean isTop) {
        this.coords = coords;
        this.width = 60;
        this.height = 15;
        this.isTop = isTop;
    }

    public void move(double dx, Ball ball) {
        double newX = coords.x + dx;
        if (!checkBallCollision(ball.getCoords(isTop), ball.getRadius())) {
            if (newX > PLAYGROUND_WIDTH / 2 - width / 2) {
                newX = PLAYGROUND_WIDTH / 2 - width / 2;
            } else if (newX < -PLAYGROUND_WIDTH / 2 + width / 2) {
                newX = -PLAYGROUND_WIDTH / 2 + width / 2;
            }
            coords.x = newX;
        }
    }

    public boolean checkBallCollision(BallCoords ballCoords, double radius) {
        if (isTop) {
            return ballCoords.y < height + radius &&
                    ballCoords.x < coords.x + width/2 + radius &&
                    ballCoords.x > coords.x - width/2 - radius;
        } else {
            return ballCoords.y > PLAYGROUND_HEIGHT - height - radius &&
                    ballCoords.x < coords.x + width/2 + radius &&
                    ballCoords.x > coords.x - width/2 - radius;
        }
    }

    public PlatformCoords getCoords() {
        return coords;
    }

    public double getHeight() {
        return height;
    }

    public double getWidth() {
        return width;
    }
}
