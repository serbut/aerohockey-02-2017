package com.aerohockey.responses;

import com.aerohockey.model.UserProfile;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class LeaderboardResponse {
    private final List<UserResponse> users;

    @JsonCreator
    public LeaderboardResponse(@JsonProperty("users") List<UserProfile> users) {
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