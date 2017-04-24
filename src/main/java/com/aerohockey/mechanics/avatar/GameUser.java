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
    private int score;

    public GameUser(UserProfile userProfile) {
        this.userProfile = userProfile;
        platform = new Platform(new PlatformCoords(0));
    }

    public long getId() {
        return userProfile.getId();
    }

    public @NotNull ServerPlayerSnap generateSnap() {
        final ServerPlayerSnap result = new ServerPlayerSnap();
        result.setUserId(getId());
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

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}