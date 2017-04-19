package com.aerohockey.mechanics.internal;

import com.aerohockey.mechanics.GameSession;
import com.aerohockey.mechanics.avatar.GameUser;
import com.aerohockey.mechanics.base.ServerPlayerSnap;
import com.aerohockey.mechanics.requests.StartGame;
import com.aerohockey.websocket.Message;
import com.aerohockey.websocket.RemotePointService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;

import java.io.IOException;
import java.util.*;

/**
 * Created by sergeybutorin on 15.04.17.
 */
@Service
public class GameInitService {
    private static final Logger LOGGER = LoggerFactory.getLogger(GameInitService.class);

    private final @NotNull RemotePointService remotePointService;
    private final @NotNull ObjectMapper objectMapper = new ObjectMapper();

    public GameInitService(@NotNull RemotePointService remotePointService) {
        this.remotePointService = remotePointService;
    }

    public void initGameFor(@NotNull GameSession gameSession) {
        gameSession.getFirst();
        gameSession.getSecond();
        final Collection<GameUser> players = new ArrayList<>();
        players.add(gameSession.getFirst());
        players.add(gameSession.getSecond());
        for (GameUser player : players) {
            final StartGame.Request initMessage = createInitMessageFor(gameSession, player.getId());
            //noinspection OverlyBroadCatchBlock
            try {
                final Message message = new Message(StartGame.Request.class.getName(),
                        objectMapper.writeValueAsString(initMessage));
                remotePointService.sendMessageToUser(player.getId(), message);
            } catch (IOException e) {
                //TODO: Reentrance mechanism
                players.forEach(playerToCutOff -> remotePointService.cutDownConnection(playerToCutOff.getId(),
                        CloseStatus.SERVER_ERROR));
                LOGGER.error("Unnable to start a game", e);
            }
        }
    }

    @SuppressWarnings("TooBroadScope")
    private StartGame.Request createInitMessageFor(@NotNull GameSession gameSession, @NotNull Long userId) {
        final GameUser self = gameSession.getSelf(userId);
        final StartGame.Request initGameMessage = new StartGame.Request();

        final List<ServerPlayerSnap> playerSnaps = new ArrayList<>();

        final Collection<GameUser> players = new ArrayList<>();
        players.add(gameSession.getFirst());
        players.add(gameSession.getSecond());
        for (GameUser player : players) {
            playerSnaps.add(player.generateSnap());
        }

        initGameMessage.setSelf(userId);
        initGameMessage.setPlayers(playerSnaps);
        return initGameMessage;
    }
}
