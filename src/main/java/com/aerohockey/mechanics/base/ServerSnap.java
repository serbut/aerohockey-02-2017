package com.aerohockey.mechanics.base;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Created by sergeybutorin on 15.04.17.
 */
public class ServerSnap {
    @NotNull List<ServerPlayerSnap> players;
    @NotNull BallCoords ballCoords;
    private long serverFrameTime;

    public @NotNull List<ServerPlayerSnap> getPlayers() {
        return players;
    }

    public void setPlayers(@NotNull List<ServerPlayerSnap> players) {
        this.players = players;
    }

    public @NotNull BallCoords ballCoords() {
        return ballCoords;
    }

    public void setBallCoords(BallCoords ballCoords) {
        this.ballCoords = ballCoords;
    }

    public long getServerFrameTime() {
        return serverFrameTime;
    }

    public void setServerFrameTime(long serverFrameTime) {
        this.serverFrameTime = serverFrameTime;
    }
}
