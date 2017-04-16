package com.aerohockey.mechanics.handlers;

import com.aerohockey.mechanics.GameMechanics;
import com.aerohockey.mechanics.requests.JoinGame;
import com.aerohockey.mechanics.requests.StartGame;
import com.aerohockey.websocket.HandleException;
import com.aerohockey.websocket.MessageHandler;
import com.aerohockey.websocket.MessageHandlerContainer;
import org.jetbrains.annotations.NotNull;

import javax.annotation.PostConstruct;

/**
 * Created by sergeybutorin on 13.04.17.
 */
public class JoinGameHandler extends MessageHandler<JoinGame.Request> {
    private @NotNull GameMechanics gameMechanics;
    private @NotNull MessageHandlerContainer messageHandlerContainer;

    public JoinGameHandler(@NotNull GameMechanics gameMechanics, @NotNull MessageHandlerContainer messageHandlerContainer) {
        super(JoinGame.Request.class);
        this.gameMechanics = gameMechanics;
        this.messageHandlerContainer = messageHandlerContainer;
    }

    @PostConstruct
    private void init() {
        messageHandlerContainer.registerHandler(JoinGame.Request.class, this);
    }

    @Override
    public void handle(@NotNull JoinGame.Request message, @NotNull Long forUser) throws HandleException {
        gameMechanics.addUser(forUser);
    }
}
