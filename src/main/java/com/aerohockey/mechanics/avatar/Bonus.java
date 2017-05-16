package com.aerohockey.mechanics.avatar;

import com.aerohockey.mechanics.GameSession;
import com.aerohockey.mechanics.base.Coords;
import org.jetbrains.annotations.NotNull;

import java.time.ZonedDateTime;

import static com.aerohockey.mechanics.Config.*;

/**
 * Created by sergeybutorin on 15/05/2017.
 */
public class Bonus {

    public enum Types {
        BALL_INCREASE,
        BALL_DECREASE,
        BALL_MULTIPLY,
        PLATFORM_INCREASE,
        PLATFORM_DECREASE;

        public static Types getRandom() {
            return values()[(int) (Math.random() * values().length)];
        }
    }

    private final ZonedDateTime expired;
    private final Coords coords;
    private final Types type;
    private Ball ball;

    public Bonus() {
        coords = new Coords(generateCoord(PLAYGROUND_WIDTH / 4, 3 * PLAYGROUND_WIDTH / 4),
                generateCoord(PLAYGROUND_HEIGHT / 4, 3 * PLAYGROUND_HEIGHT / 4));
        type = Types.getRandom();
        expired = ZonedDateTime.now().plusSeconds(BONUS_EXPIRED_TIME);
    }

    public boolean checkBonusCollision(@NotNull Coords ballCoords) {
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

    public void execute(@NotNull GameSession gameSession, @NotNull Ball pickedUpBall) {
        this.ball = pickedUpBall;
        switch (type) {
            case BALL_DECREASE:
                ball.setRadius(BALL_RADIUS / 2);
                break;
            case BALL_INCREASE:
                ball.setRadius(BALL_RADIUS * 2);
                break;
            case BALL_MULTIPLY:
                //TODO
                break;
            case PLATFORM_DECREASE:
                //TODO

                break;
            case PLATFORM_INCREASE:
                break;
        }
    }

    public void deactivate(@NotNull GameSession gameSession) {
        switch (type) {
            case BALL_DECREASE:
            case BALL_INCREASE:
                ball.setRadius(BALL_RADIUS);
                break;
            case BALL_MULTIPLY:
                break;
            case PLATFORM_DECREASE:
                break;
            case PLATFORM_INCREASE:
                break;
        }
    }

    public double generateCoord(int min, int max) {
        max -= min;
        return (Math.random() * ++max) + min;
    }
}
