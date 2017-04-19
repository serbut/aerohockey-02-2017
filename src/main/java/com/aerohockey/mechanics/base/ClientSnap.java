package com.aerohockey.mechanics.base;

import org.jetbrains.annotations.NotNull;

/**
 * Created by sergeybutorin on 14.04.17.
 */
public class ClientSnap {
    private @NotNull PlatformCoords platformCoords;

    public @NotNull PlatformCoords getPlatformCoords() {
        return platformCoords;
    }

    public void setPlatformCoords(PlatformCoords platformCoords) {
        this.platformCoords = platformCoords;
    }
}
