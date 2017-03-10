package com.aerohockey.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.concurrent.atomic.AtomicLong;
import java.util.Random;


/**
 * Created by sergeybutorin on 20.02.17.
 */

public class UserProfile {
    private static final AtomicLong ID_GENERATOR = new AtomicLong(0);
    static final Random randomGenerator = new Random();

    private long id;
    @JsonProperty("login")
    private String login;
    @JsonProperty("email")
    private String email;
    @JsonProperty("password")
    private String password;
    @JsonProperty("old-password")
    private String oldPassword;
    private int rating;

    @SuppressWarnings("unused")
    private UserProfile() {
    }

    public UserProfile(String login, String email, String password) {
        this.id = ID_GENERATOR.getAndIncrement();
        this.login = login;
        this.email = email;
        this.password = password;
        this.rating = randomGenerator.nextInt(100);
    }

    @SuppressWarnings("unused")
    public long getId() {
        return id;
    }

    @SuppressWarnings("unused")
    public String getLogin() {
        return login;
    }

    @SuppressWarnings("unused")
    public String getPassword() {
        return password;
    }

    @SuppressWarnings("unused")
    public String getEmail() {
        return email;
    }

    public String getOldPassword() {
        return oldPassword;
    }


    @SuppressWarnings("unused")
    public int getRating() {
        return rating;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
