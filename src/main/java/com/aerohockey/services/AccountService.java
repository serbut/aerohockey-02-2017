package com.aerohockey.services;

import com.aerohockey.model.UserProfile;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.*;

/**
 * Created by sergeybutorin on 18.02.17.
 */

@Service
public class AccountService {
    private final Map<String, UserProfile> userNameToUserProfile = new HashMap<>();

    @NotNull
    public UserProfile addUser(@NotNull String login, @NotNull String email, @NotNull String password) {
        final UserProfile newUser = new UserProfile(login, email, password);
        userNameToUserProfile.put(login, newUser);
        return newUser;
    }

    public UserProfile getUserByLogin(String login) {
        return userNameToUserProfile.get(login);
    }

    public List<UserProfile> getLeaders() {
        final List<UserProfile> leaders = new ArrayList<>();
        for (UserProfile user : userNameToUserProfile.values()) {
            leaders.add(user);
        }
        return leaders;
    }

    public void changeData(UserProfile newUser) {
        userNameToUserProfile.replace(newUser.getLogin(), newUser);
    }

    public void addSomeUsers(int count) {
        for (int i = 0; i < count; i++) {
            final String login = generateRandomString(10);
            final String email = login + "@mail.ru";
            final String password = "123";
            addUser(login, email, password);
        }
    }

    public String generateRandomString(int length) {
        final char[] chars = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        final StringBuilder sb = new StringBuilder();
        final Random random = new Random();
        for (int i = 0; i < length; i++) {
            char c = chars[random.nextInt(chars.length)];
            sb.append(c);
        }
        return sb.toString();
    }
}
