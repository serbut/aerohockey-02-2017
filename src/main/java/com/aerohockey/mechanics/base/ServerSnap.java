package com.aerohockey.mechanics.base;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Created by sergeybutorin on 15.04.17.
 */
public class ServerSnap {
    @NotNull List<ServerPlayerSnap> players;

    public @NotNull List<ServerPlayerSnap> getPlayers() {
        return players;
    }

    public void setPlayers(@NotNull List<ServerPlayerSnap> players) {
        this.players = players;
    }


}
