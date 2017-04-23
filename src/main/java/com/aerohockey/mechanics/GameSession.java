package com.aerohockey.mechanics;

import com.aerohockey.mechanics.avatar.GameUser;
import com.aerohockey.mechanics.base.BallCoords;
import com.aerohockey.model.UserProfile;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.atomic.AtomicLong;

import static com.aerohockey.mechanics.Config.BALL_RADIUS;
import static com.aerohockey.mechanics.Config.PLATFORM_HEIGHT;
import static com.aerohockey.mechanics.Config.PLAYGROUND_WIDTH;

/**
 * Created by sergeybutorin on 14.04.17.
 */
public class GameSession {
    private static final AtomicLong ID_GENERATOR = new AtomicLong(0);
    private final @NotNull Long sessionId;
    private final @NotNull GameUser first;
    private final @NotNull GameUser second;
    private @NotNull BallCoords ball;

    public GameSession(@NotNull UserProfile user1, @NotNull UserProfile user2) {
        this.sessionId = ID_GENERATOR.getAndIncrement();
        this.first = new GameUser(user1);
        this.second =  new GameUser(user2);
        this.ball = new BallCoords(PLAYGROUND_WIDTH/2, PLATFORM_HEIGHT + BALL_RADIUS);
    }

    public @NotNull GameUser getFirst() {
        return first;
    }

    public @NotNull GameUser getSecond() {
        return second;
    }

    public @NotNull BallCoords getBall() {
        return ball;
    }

    public void setBall(@NotNull BallCoords ball) {
        this.ball = ball;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final GameSession session = (GameSession) o;

        return sessionId.equals(session.sessionId);

    }

    @Override
    public int hashCode() {
        return sessionId.hashCode();
    }
}
