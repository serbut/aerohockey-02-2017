package com.aerohockey.services;

import com.aerohockey.model.UserProfile;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;

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

    public void changeData(UserProfile newUser) {
        userNameToUserProfile.replace(newUser.getLogin(), newUser);
    }
}
