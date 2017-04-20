package com.aerohockey.mechanics.internal;

import com.aerohockey.mechanics.GameSession;
import com.aerohockey.mechanics.avatar.GameUser;
import com.aerohockey.mechanics.base.ClientSnap;
import com.aerohockey.mechanics.base.PlatformCoords;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.*;

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
            final ClientSnap lastSnap = playerSnaps.get(playerSnaps.size() - 1);

            //TODO:Firing
        }
    }

    private void processMovement(@NotNull GameUser gameUser, @NotNull String direction) {
        final PlatformCoords platform = gameUser.getPlatform();
        switch (direction) {
            case "left": {
                gameUser.setPlatform(movePlatform(platform, -10));
                break;
            }
            case "right": {
                gameUser.setPlatform(movePlatform(platform, 10));
                break;
            }
            default: {
                break;
            }
        }
    }

    private PlatformCoords movePlatform(@NotNull PlatformCoords platform, double dx) {
        final PlatformCoords newCoords = new PlatformCoords(platform.x + dx);
        if (newCoords.x > PLAYGROUND_WIDTH - platform.platformWidth/2) {
            newCoords.x = PLAYGROUND_WIDTH - platform.platformWidth/2;
        } else if (newCoords.x < platform.platformWidth/2) {
            newCoords.x = platform.platformWidth/2;
        }
        return newCoords;

    }

    public void clear() {
        snaps.clear();
    }
}
