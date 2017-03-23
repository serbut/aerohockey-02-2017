package com.aerohockey.controller;

import com.aerohockey.model.UserProfile;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sergey on 20.03.17.
 */
public class Responses {
    static final class ErrorResponse {
        static final String NOT_AUTHORIZED = "User not authorized";
        private final String error;

        ErrorResponse(String error) {
            this.error = error;
        }

        public static ErrorResponse notAuthorized() {
            return new ErrorResponse("User not authorized");
        }

        @SuppressWarnings("unused")
        public String getError() {
            return error;
        }
    }

    static final class UserResponse {
        private final String login;
        private final String email;
        private final int rating;

        UserResponse(UserProfile user) {
            this.login = user.getLogin();
            this.email = user.getEmail();
            this.rating = user.getRating();
        }

        @SuppressWarnings("unused")
        public String getLogin() {
            return login;
        }

        @SuppressWarnings("unused")
        public String getEmail() {
            return email;
        }

        @SuppressWarnings("unused")
        public int getRating() {
            return rating;
        }
    }

    static final class LeaderboardResponse {
        private final List<UserResponse> users;

        LeaderboardResponse(List<UserProfile> users) {
            this.users = new ArrayList<>();
            for (UserProfile user : users) {
                this.users.add(new UserResponse(user));
            }
        }

        @SuppressWarnings("unused")
        public List<UserResponse> getUsers() {
            return users;
        }
    }
}
