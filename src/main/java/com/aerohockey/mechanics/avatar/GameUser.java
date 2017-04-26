package com.aerohockey.mechanics.avatar;

import com.aerohockey.mechanics.base.PlatformCoords;
import com.aerohockey.mechanics.base.ServerPlayerSnap;
import com.aerohockey.model.UserProfile;
import org.jetbrains.annotations.NotNull;

/**
 * Created by sergeybutorin on 15.04.17.
 */
public class GameUser {
    private final UserProfile userProfile;
    private final Platform platform;
    private byte score;

    public GameUser(UserProfile userProfile) {
        this.userProfile = userProfile;
        this.score = 0;
        platform = new Platform(new PlatformCoords());
    }

    public long getId() {
        return userProfile.getId();
    }

    public void addScore() {
        this.score += 1;
    }

    public @NotNull ServerPlayerSnap generateSnap() {
        final ServerPlayerSnap result = new ServerPlayerSnap();
        result.setUserId(getId());
        result.setScore(score);
        result.setPlatform(platform.getCoords());
        return result;
    }

    public Platform getPlatform() {
        return platform;
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