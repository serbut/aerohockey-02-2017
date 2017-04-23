package com.aerohockey.mechanics.internal;

import com.aerohockey.mechanics.GameSession;
import com.aerohockey.mechanics.avatar.GameUser;
import com.aerohockey.mechanics.base.ClientSnap;
import com.aerohockey.mechanics.base.Platform;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.aerohockey.mechanics.Config.PLATFORM_WIDTH;
import static com.aerohockey.mechanics.Config.PLAYGROUND_WIDTH;

/**
 * Created by sergeybutorin on 15.04.17.
 */
@Service
public class ClientSnapService {
    private final Map<Long, List<ClientSnap>> snaps = new HashMap<>();

    public void pushClientSnap(@NotNull Long user, @NotNull ClientSnap snap) {
        this.snaps.putIfAbsent(user, new ArrayList<>());
        final List<ClientSnap> clientSnaps = snaps.get(user);
        clientSnaps.add(snap);
    }

    public @NotNull List<ClientSnap> getSnapForUser(@NotNull Long user) {
        return snaps.getOrDefault(user, Collections.emptyList());
    }

    public void processSnapshotsFor(@NotNull GameSession gameSession) {
        final Collection<GameUser> players = new ArrayList<>();
        players.add(gameSession.getFirst());
        players.add(gameSession.getSecond());
        for (GameUser player : players) {
            final List<ClientSnap> playerSnaps = getSnapForUser(player.getId());
            if (playerSnaps.isEmpty()) {
                continue;
            }
            for (ClientSnap snap : playerSnaps) {
                processMovement(player, snap.getDirection());
            }
        }
    }

    private void processMovement(@NotNull GameUser gameUser, @NotNull String direction) {
        final Platform platform = gameUser.getPlatform();
        switch (direction) {
            case "left": {
                gameUser.setPlatform(movePlatform(platform, -1));
                break;
            }
            case "right": {
                gameUser.setPlatform(movePlatform(platform, 1));
                break;
            }
            default: {
                break;
            }
        }
    }

    private Platform movePlatform(@NotNull Platform platform, double dx) {
        final Platform newCoords = new Platform(platform.x + dx);
        if (newCoords.x > PLAYGROUND_WIDTH - PLATFORM_WIDTH/2) {
            newCoords.x = PLAYGROUND_WIDTH - PLATFORM_WIDTH/2;
        } else if (newCoords.x < PLATFORM_WIDTH/2) {
            newCoords.x = PLATFORM_WIDTH/2;
        }
        return newCoords;

    }

    public void clear() {
        snaps.clear();
    }
}
