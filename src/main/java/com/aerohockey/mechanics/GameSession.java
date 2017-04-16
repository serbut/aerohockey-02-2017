package com.aerohockey.mechanics;

import com.aerohockey.mechanics.avatar.GameUser;
import com.aerohockey.model.UserProfile;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by sergeybutorin on 14.04.17.
 */
public class GameSession {
    private static final AtomicLong ID_GENERATOR = new AtomicLong(0);
    private final @NotNull Long sessionId;
    private final @NotNull GameUser first;
    private final @NotNull GameUser second;

    public GameSession(@NotNull UserProfile user1, @NotNull UserProfile user2) {
        this.sessionId = ID_GENERATOR.getAndIncrement();
        this.first = new GameUser(user1);
        this.second =  new GameUser(user2);
    }

    public @NotNull GameUser getEnemy(@NotNull GameUser user) {
        if (user == first) {
            return second;
        }
        if (user == second) {
            return first;
        }
        throw new IllegalArgumentException("Requested enemy for game but user not participant");
    }

    public @NotNull GameUser getSelf(@NotNull Long userId) {
        if (first.getUserProfile().getId() == userId) {
            return first;
        }
        if (second.getUserProfile().getId() == userId) {
            return second;
        }
        throw new IllegalArgumentException("Request self for game but user not participate it");
    }

    public @NotNull GameUser getFirst() {
        return first;
    }

    public @NotNull GameUser getSecond() {
        return second;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final GameSession that = (GameSession) o;

        return sessionId.equals(that.sessionId);

    }

    @Override
    public int hashCode() {
        return sessionId.hashCode();
    }
}
