package com.aerohockey.mechanics.requests;

import com.aerohockey.mechanics.base.ServerPlayerSnap;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Created by sergeybutorin on 13.04.17.
 */
public class StartGame {
    @SuppressWarnings({"unused", "NullableProblems"})
    public static class Request {
        private @NotNull Long self;
        private @NotNull String opponentLogin;
        private @NotNull Integer opponentRating;
        private @NotNull int [] coordsTransform;
        private @NotNull List<ServerPlayerSnap> players;

        public @NotNull Long getSelf() {
            return self;
        }

        public void setSelf(@NotNull Long self) {
            this.self = self;
        }

        public String getOpponentLogin() {
            return opponentLogin;
        }

        public void setOpponentLogin(String opponentLogin) {
            this.opponentLogin = opponentLogin;
        }

        public Integer getOpponentRating() {
            return opponentRating;
        }

        public void setOpponentRating(Integer opponentRating) {
            this.opponentRating = opponentRating;
        }

        public @NotNull List<ServerPlayerSnap> getPlayers() {
            return players;
        }

        public void setPlayers(@NotNull List<ServerPlayerSnap> players) {
            this.players = players;
        }

        public int[] getCoordsTransform() {
            return coordsTransform;
        }

        public void setCoordsTransform(int[] coordsTransform) {
            this.coordsTransform = coordsTransform;
        }
    }
}
