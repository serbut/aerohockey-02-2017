package com.aerohockey.mechanics.requests;

import com.aerohockey.mechanics.base.ServerPlayerSnap;
import com.aerohockey.model.UserProfile;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;

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
        public @NotNull void setPlayers(@NotNull List<ServerPlayerSnap> players) {
            this.players = players;
        }
    }
}
