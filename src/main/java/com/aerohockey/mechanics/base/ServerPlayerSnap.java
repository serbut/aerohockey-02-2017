package com.aerohockey.mechanics.base;

import org.jetbrains.annotations.NotNull;

/**
 * Created by sergeybutorin on 16.04.17.
 */
public class ServerPlayerSnap {
    private @NotNull Long userId;

    private PlatformCoords platformCoords;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(long id) {
        userId = id;
    }
}
