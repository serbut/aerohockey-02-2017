package com.aerohockey.responses;

import com.aerohockey.model.UserProfile;

import java.util.ArrayList;
import java.util.List;

public class LeaderboardResponse {
    private final List<UserResponse> users;

    public LeaderboardResponse(List<UserProfile> users) {
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