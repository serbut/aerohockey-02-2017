package com.aerohockey.mechanics.avatar;

import com.aerohockey.mechanics.GameSession;
import com.aerohockey.mechanics.base.BonusSnap;
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
        coords = new Coords(generateCoord(-PLAYGROUND_WIDTH / 4, PLAYGROUND_WIDTH / 4),
                generateCoord(-PLAYGROUND_HEIGHT / 4, PLAYGROUND_HEIGHT / 4));
        type = Types.getRandom();
        expired = ZonedDateTime.now().plusSeconds(BONUS_EXPIRED_TIME);
    }

    public boolean checkBonusCollision(@NotNull Ball ball) {
        return (Math.pow((ball.getCoords().x - coords.x), 2) + Math.pow((ball.getCoords().y - coords.y), 2)) < (ball.getRadius() + BONUS_SIZE) * (ball.getRadius() + BONUS_SIZE);
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
                //TODO: создавать шарики того же радиуса и с той же скоростью
                gameSession.addBall(new Coords(ball.getCoords().x + 3 * ball.getRadius(), ball.getCoords().y));
                gameSession.addBall(new Coords(ball.getCoords().x - 3 * ball.getRadius(), ball.getCoords().y));
                break;
            case PLATFORM_DECREASE:
                ball.getUser(gameSession).getPlatform().setWidth(PLATFORM_WIDTH / PLATFORM_WIDTH_CHANGE);
                break;
            case PLATFORM_INCREASE:
                ball.getUser(gameSession).getPlatform().setWidth(PLATFORM_WIDTH * PLATFORM_WIDTH_CHANGE);
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
            case PLATFORM_INCREASE:
                gameSession.getTop().getPlatform().setWidth(PLATFORM_WIDTH);
                gameSession.getBottom().getPlatform().setWidth(PLATFORM_WIDTH);
                break;
        }
    }

    public double generateCoord(int min, int max) {
        max -= min;
        return (Math.random() * ++max) + min;
    }

    public BonusSnap getSnap() {
        return new BonusSnap(coords, type.toString());
    }
}
