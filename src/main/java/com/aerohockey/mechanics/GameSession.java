package com.aerohockey.mechanics;

import com.aerohockey.mechanics.avatar.Ball;
import com.aerohockey.mechanics.avatar.GameUser;
import com.aerohockey.mechanics.base.BallCoords;
import com.aerohockey.model.UserProfile;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.atomic.AtomicLong;

import static com.aerohockey.mechanics.Config.*;

/**
 * Created by sergeybutorin on 14.04.17.
 */
public class GameSession {
    private static final AtomicLong ID_GENERATOR = new AtomicLong(0);
    private final @NotNull Long sessionId;
    private final @NotNull GameUser first;
    private final @NotNull GameUser second;
    private @NotNull Ball ball;

    public GameSession(@NotNull UserProfile user1, @NotNull UserProfile user2) {
        this.sessionId = ID_GENERATOR.getAndIncrement();
        this.first = new GameUser(user1);
        this.second =  new GameUser(user2);
        this.ball = new Ball(new BallCoords(0, PLAYGROUND_HEIGHT/2));
    }

    public @NotNull GameUser getFirst() {
        return first;
    }

    public @NotNull GameUser getSecond() {
        return second;
    }

    public @NotNull Ball getBall() {
        return ball;
    }

    public void setBallCoords(@NotNull BallCoords ballCoords) {
        this.ball.setCoords(ballCoords);
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
