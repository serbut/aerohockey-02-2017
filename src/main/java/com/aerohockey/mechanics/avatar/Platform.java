package com.aerohockey.mechanics.avatar;

import com.aerohockey.mechanics.base.Coords;
import com.aerohockey.mechanics.base.PlatformCoords;
import org.jetbrains.annotations.NotNull;

import static com.aerohockey.mechanics.Config.PLATFORM_WIDTH;
import static com.aerohockey.mechanics.Config.PLAYGROUND_HEIGHT;
import static com.aerohockey.mechanics.Config.PLAYGROUND_WIDTH;

/**
 * Created by sergeybutorin on 23.04.17.
 */
public class Platform {
    private final PlatformCoords coords;
    @SuppressWarnings("InstanceVariableNamingConvention")
    private final double y;
    private double width;
    private final double height;

    public Platform(boolean isTop) {
        this.coords = new PlatformCoords();
        this.width = PLATFORM_WIDTH;
        this.height = 15;
        this.y = isTop ? PLAYGROUND_HEIGHT/2 : -PLAYGROUND_HEIGHT/2;
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

    public boolean checkBallCollision(@NotNull Coords ballCoords, double radius) {
        return Math.abs(y - ballCoords.y) < height + radius &&
                Math.abs(this.coords.x - ballCoords.x) < width/2 + radius;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public PlatformCoords getCoords() {
        return coords;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public double getY() {
        return y;
    }
}
