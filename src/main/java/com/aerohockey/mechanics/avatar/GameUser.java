package com.aerohockey.mechanics.avatar;

import com.aerohockey.mechanics.base.PlatformCoords;
import com.aerohockey.mechanics.base.ServerPlayerSnap;
import com.aerohockey.model.UserProfile;
import org.jetbrains.annotations.NotNull;

import static com.aerohockey.mechanics.Config.PLAYGROUND_HEIGHT;

/**
 * Created by sergeybutorin on 15.04.17.
 */
public class GameUser {
    private final UserProfile userProfile;
    private final Platform platform;
    private byte score;
    private final boolean isTop;
    private final int [] coordsTransform;

    public GameUser(UserProfile userProfile, boolean isTop) {
        this.userProfile = userProfile;
        this.score = 0;
        this.isTop = isTop;
        this.coordsTransform = isTop ? new int[]{1, 1, 1, 0} : new int[]{-1, -1, -1, PLAYGROUND_HEIGHT};
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

    public byte getScore() {
        return score;
    }

    public int[] getCoordsTransform() {
        return coordsTransform;
    }

    public void changeRating(int value) {
        userProfile.changeRating(value);
    }

    public @NotNull ServerPlayerSnap generateSnap() {
        final PlatformCoords platformCoords = new PlatformCoords(platform.getCoords().x);

        final ServerPlayerSnap result = new ServerPlayerSnap();
        result.setUserId(getId());
        result.setScore(score);
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