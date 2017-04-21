package com.aerohockey.mechanics.handlers;

import com.aerohockey.mechanics.GameMechanics;
import com.aerohockey.mechanics.base.ClientSnap;
import com.aerohockey.websocket.HandleException;
import com.aerohockey.websocket.MessageHandler;
import com.aerohockey.websocket.MessageHandlerContainer;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created by sergeybutorin on 16.04.17.
 */
@Component
public class ClientSnapHandler extends MessageHandler<ClientSnap> {
    private final @NotNull GameMechanics gameMechanics;
    private final @NotNull MessageHandlerContainer messageHandlerContainer;

    public ClientSnapHandler(@NotNull GameMechanics gameMechanics, @NotNull MessageHandlerContainer messageHandlerContainer) {
        super(ClientSnap.class);
        this.gameMechanics = gameMechanics;
        this.messageHandlerContainer = messageHandlerContainer;
    }

    @PostConstruct
    private void init() {
        messageHandlerContainer.registerHandler(ClientSnap.class, this);
    }

    @Override
    public void handle(@NotNull ClientSnap message, @NotNull Long forUser) throws HandleException {
        gameMechanics.addClientSnapshot(forUser, message);
    }
}
