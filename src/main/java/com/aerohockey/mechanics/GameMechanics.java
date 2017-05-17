package com.aerohockey.mechanics;

import com.aerohockey.mechanics.base.ClientSnap;
import org.jetbrains.annotations.NotNull;

/**
 * Created by sergeybutorin on 13.04.17.
 */
public interface GameMechanics {
    void addClientSnapshot(@NotNull Long userId, @NotNull ClientSnap gameSnap);

    void addUser(@NotNull Long user);

    void gmStep(long frameTime);

    @SuppressWarnings("EmptyMethod")
    void reset();
}
