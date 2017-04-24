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
    private PlatformCoords coords;

    private double width;
    private double height;

    @SuppressWarnings("MagicNumber")
    public Platform(@NotNull PlatformCoords coords) {
        this.coords = coords;
        this.width = 60;
        this.height = 20;
    }

    public void move(double dx) {
        double newX = coords.x + dx;
        if (newX > PLAYGROUND_WIDTH/2 - width/2) {
            newX = PLAYGROUND_WIDTH/2 - width/2;
        } else if (newX < -PLAYGROUND_WIDTH/2 + width/2) {
            newX = -PLAYGROUND_WIDTH/2 + width/2;
        }
        coords.x = newX;
    }

    public boolean checkBallCollision(boolean isFirst, BallCoords ballCoords) {
        if (isFirst) {
            return ballCoords.y < height && ballCoords.x < coords.x + width / 2 && ballCoords.x > coords.x - width / 2;
        } else {
            return ballCoords.y > PLAYGROUND_HEIGHT - height && ballCoords.x < coords.x + width/2 && ballCoords.x > coords.x - width/2;
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

    public void setCoords(PlatformCoords coords) {
        this.coords = coords;
    }
}
