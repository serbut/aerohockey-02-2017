package com.aerohockey.mechanics.base;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Created by sergeybutorin on 15.04.17.
 */
@SuppressWarnings({"unused", "NullableProblems"})
public class ServerSnap {
    @NotNull List<? extends ServerPlayerSnap> players;
    @NotNull List<Coords> ballsCoords;
    private long serverFrameTime;

    public @NotNull List<? extends ServerPlayerSnap> getPlayers() {
        return players;
    }

    public void setPlayers(@NotNull List<? extends ServerPlayerSnap> players) {
        this.players = players;
    }

    public @NotNull List<Coords> getBallsCoords() {
        return ballsCoords;
    }

    public void setBallsCoords(@NotNull List<Coords> balls) {
        this.ballsCoords = balls;
    }

    public long getServerFrameTime() {
        return serverFrameTime;
    }

    public void setServerFrameTime(long serverFrameTime) {
        this.serverFrameTime = serverFrameTime;
    }
}
