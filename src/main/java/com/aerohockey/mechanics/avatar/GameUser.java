package com.aerohockey.mechanics.avatar;

import com.aerohockey.mechanics.base.Platform;
import com.aerohockey.mechanics.base.ServerPlayerSnap;
import com.aerohockey.model.UserProfile;
import org.jetbrains.annotations.NotNull;

import static com.aerohockey.mechanics.Config.*;

/**
 * Created by sergeybutorin on 15.04.17.
 */
public class GameUser {
    private final UserProfile userProfile;
    private Platform platform;

    public GameUser(UserProfile userProfile) {
        this.userProfile = userProfile;
        platform = new Platform(PLAYGROUND_WIDTH/2);
    }

    public long getId() {
        return userProfile.getId();
    }

    public @NotNull ServerPlayerSnap generateSnap() {
        final ServerPlayerSnap result = new ServerPlayerSnap();
        result.setUserId(getId());
        result.setPlatform(platform);
        return result;
    }

    public Platform getPlatform() {
        return platform;
    }

    public void setPlatform(Platform platform) {
        this.platform = platform;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final GameUser other = (GameUser) o;
        return this.getId() == other.getId();
    }

    @Override
    public int hashCode() {
        return (int)this.getId();
    }
}