package com.aerohockey.mechanics.internal;

import com.aerohockey.mechanics.GameSession;
import com.aerohockey.mechanics.avatar.GameUser;
import com.aerohockey.mechanics.base.ServerPlayerSnap;
import com.aerohockey.mechanics.base.ServerSnap;
import com.aerohockey.websocket.Message;
import com.aerohockey.websocket.RemotePointService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by sergeybutorin on 15.04.17.
 */
@Service
public class ServerSnapService {
    private final @NotNull RemotePointService remotePointService;

    private final @NotNull ObjectMapper objectMapper = new ObjectMapper();

    public ServerSnapService(@NotNull RemotePointService remotePointService) {
        this.remotePointService = remotePointService;
    }

    public void sendSnapshotsFor(@NotNull GameSession gameSession, long frameTime) {
        final Collection<GameUser> players = new ArrayList<>();
        players.add(gameSession.getTop());
        players.add(gameSession.getBottom());

        final ServerSnap snap = new ServerSnap();
        snap.setServerFrameTime(frameTime);

        //noinspection OverlyBroadCatchBlock
        try {
            for (GameUser player : players) {
                snap.setBallCoords(gameSession.getBall().getCoords());
                final List<ServerPlayerSnap> playersSnaps = new ArrayList<>();
                for (GameUser p : players) {
                    playersSnaps.add(p.generateSnap());
                }
                snap.setPlayers(playersSnaps);
                final Message message = new Message(ServerSnap.class.getName(), objectMapper.writeValueAsString(snap));
                remotePointService.sendMessageToUser(player.getId(), message);
            }
        } catch (IOException ex) {
            throw new RuntimeException("Failed sending snapshot", ex);
        }
    }
}
