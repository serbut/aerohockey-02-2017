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
    private final boolean isTop;

    public GameUser(UserProfile userProfile, boolean isTop) {
        this.userProfile = userProfile;
        this.score = 0;
        this.isTop = isTop;
        platform = new Platform(new PlatformCoords(0), isTop);
    }

    public int getRating() {
        return userProfile.getRating();
    }

    public String getLogin() {
        return userProfile.getLogin();
    }

    public long getId() {
        return userProfile.getId();
    }

    public void addScore() {
        this.score += 1;
    }

    public @NotNull ServerPlayerSnap generateSnap(boolean self) {
        final PlatformCoords platformCoords = new PlatformCoords(isTop ? -platform.getCoords().x : platform.getCoords().x);

        final ServerPlayerSnap result = new ServerPlayerSnap();
        result.setUserId(getId());
        result.setScore(score);
        if(!self) {
            platformCoords.x *= -1;
        }
        result.setPlatform(platformCoords);
        return result;
    }

    public Platform getPlatform() {
        return platform;
    }

    public boolean isTop() {
        return isTop;
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