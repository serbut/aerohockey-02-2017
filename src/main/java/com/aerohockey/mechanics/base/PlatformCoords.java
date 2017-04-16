package com.aerohockey.mechanics.base;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;

/**
 * Created by sergeybutorin on 14.04.17.
 */
public class PlatformCoords {
    public PlatformCoords(@JsonProperty("x") double x) {
        this.x = x;
    }

    public final double x;

    public @NotNull PlatformCoords add(@NotNull PlatformCoords addition) {
        return new PlatformCoords(x + addition.x);
    }
}
