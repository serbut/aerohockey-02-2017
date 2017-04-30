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
    private final @NotNull GameUser top;
    private final @NotNull GameUser bottom;
    private final @NotNull Ball ball;

    public GameSession(@NotNull UserProfile user1, @NotNull UserProfile user2) {
        this.sessionId = ID_GENERATOR.getAndIncrement();
        this.top = new GameUser(user1, true);
        this.bottom =  new GameUser(user2, false);
        this.ball = new Ball(new BallCoords(0, PLAYGROUND_HEIGHT/2));
    }

    public @NotNull GameUser getTop() {
        return top;
    }

    public @NotNull GameUser getBottom() {
        return bottom;
    }

    public @NotNull Ball getBall() {
        return ball;
    }

    public boolean isGameOver() {
        if (top.getScore() == 7) {
            top.changeRating(1 + bottom.getRating() / (bottom.getScore() + 1));
            bottom.changeRating(-bottom.getRating()/10);
            return true;
        } else if (bottom.getScore() == 7) {
            bottom.changeRating(top.getRating() / (top.getScore() + 1));
            top.changeRating(-top.getRating()/10);
            return true;
        }
        return false;
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
