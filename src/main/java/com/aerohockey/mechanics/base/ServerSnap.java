package com.aerohockey.mechanics.base;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Created by sergeybutorin on 15.04.17.
 */
@SuppressWarnings({"unused", "NullableProblems"})
public class ServerSnap {
    @NotNull List<ServerPlayerSnap> players;
    @NotNull Coords ballCoords;
    private long serverFrameTime;

    public @NotNull List<ServerPlayerSnap> getPlayers() {
        return players;
    }

    public void setPlayers(@NotNull List<ServerPlayerSnap> players) {
        this.players = players;
    }

    public @NotNull Coords getBallCoords() {
        return ballCoords;
    }

    public void setBallCoords(@NotNull Coords coords) {
        this.ballCoords = coords;
    }

    public long getServerFrameTime() {
        return serverFrameTime;
    }

    public void setServerFrameTime(long serverFrameTime) {
        this.serverFrameTime = serverFrameTime;
    }
}
