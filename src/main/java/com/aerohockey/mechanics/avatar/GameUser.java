package com.aerohockey.mechanics.avatar;

import com.aerohockey.mechanics.base.PlatformCoords;
import com.aerohockey.mechanics.base.ServerPlayerSnap;
import com.aerohockey.model.UserProfile;
import org.jetbrains.annotations.NotNull;

import static com.aerohockey.mechanics.Config.*;

/**
 * Created by sergeybutorin on 15.04.17.
 */
public class GameUser {
    private final UserProfile userProfile;
    private PlatformCoords platform; // хз хз

    public GameUser(UserProfile userProfile) {
        this.userProfile = userProfile;
        platform = new PlatformCoords(PLAYGROUND_WIDTH/2);
    }

    public UserProfile getUserProfile() {
        return userProfile;
    }

    public long getId() {
        return userProfile.getId();
    }

    public @NotNull ServerPlayerSnap generateSnap() {
        final ServerPlayerSnap result = new ServerPlayerSnap();
        result.setUserId(getId());
        result.setPlatformCoords(platform); // хз хз
        return result;
    }

    public PlatformCoords getPlatform() {
        return platform;
    }

    public void setPlatform(PlatformCoords platform) {
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