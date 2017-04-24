package com.aerohockey.mechanics.requests;

import com.aerohockey.mechanics.base.ServerPlayerSnap;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Created by sergeybutorin on 13.04.17.
 */
public class StartGame {
    public static class Request {
        private @NotNull Long self;
        private @NotNull List<ServerPlayerSnap> players;

        public @NotNull Long getSelf() {
            return self;
        }

        public void setSelf(@NotNull Long self) {
            this.self = self;
        }

        public @NotNull List<ServerPlayerSnap> getPlayers() {
            return players;
        }

        public void setPlayers(@NotNull List<ServerPlayerSnap> players) {
            this.players = players;
        }
    }
}
