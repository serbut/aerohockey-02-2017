package com.aerohockey.mechanics.avatar;

import com.aerohockey.mechanics.base.Coords;

import java.time.ZonedDateTime;

import static com.aerohockey.mechanics.Config.*;

/**
 * Created by sergeybutorin on 15/05/2017.
 */
public class Bonus {

    public enum Types {

        BALL_INCREASE,
        BALL_MULTIPLY,
        PLATFORM_INCREASE;

        public static Types getRandom() {
            return values()[(int) (Math.random() * values().length)];
        }
    }

    private final ZonedDateTime expired;
    private final Coords coords;
    private final Types type;

    public Bonus() {
        coords = new Coords(generateCoord(PLAYGROUND_WIDTH / 4, 3 * PLAYGROUND_WIDTH / 4),
                generateCoord(PLAYGROUND_HEIGHT / 4, 3 * PLAYGROUND_HEIGHT / 4));
        type = Types.getRandom();
        expired = ZonedDateTime.now().plusSeconds(BONUS_EXPIRED_TIME);
    }

    public boolean checkBonusCollision(Coords ballCoords) {
        return Math.abs(ballCoords.x - coords.x) < BONUS_SIZE;
    }

    public Coords getCoords() {
        return coords;
    }

    public Types getType() {
        return type;
    }

    public ZonedDateTime getExpired() {
        return expired;
    }

    public double generateCoord(int min, int max) {
        max -= min;
        return (Math.random() * ++max) + min;
    }
}
