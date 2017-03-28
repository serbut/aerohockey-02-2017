package com.aerohockey.responses;

import com.aerohockey.model.UserProfile;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.stream.Collectors;

public class LeaderboardResponse {
    private final List<UserResponse> users;

    @JsonCreator
    public LeaderboardResponse(@JsonProperty("users") List<UserProfile> users) {
        this.users = users.stream().map(UserResponse::new).collect(Collectors.toList());
    }

    @SuppressWarnings("unused")
    public List<UserResponse> getUsers() {
        return users;
    }
}