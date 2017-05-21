package com.aerohockey.mechanics;

import com.aerohockey.mechanics.base.ClientSnap;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created by sergeybutorin on 13.04.17.
 */
public interface GameMechanics {
    void addClientSnapshot(@NotNull Long userId, @NotNull ClientSnap gameSnap);

    void addUser(@NotNull Long user);

    int getSessionCount();

    boolean isUserWaiting();

    boolean isUserPlaying(@NotNull Long user);

    @Nullable GameSession getSessionForUser(@NotNull Long userId);

    void gmStep(long frameTime);

    @SuppressWarnings("EmptyMethod")
    void reset();
}
