package com.aerohockey.responses;

import com.aerohockey.model.UserProfile;

public class UserResponse {
    private final long id;
    private final String login;
    private final String email;
    private final int rating;

    public UserResponse(UserProfile user) {
        this.id = user.getId();
        this.login = user.getLogin();
        this.email = user.getEmail();
        this.rating = user.getRating();
    }

    @SuppressWarnings("unused")
    public long getId() {
        return id;
    }

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