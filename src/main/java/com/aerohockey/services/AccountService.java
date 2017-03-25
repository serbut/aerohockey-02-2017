package com.aerohockey.services;

import com.aerohockey.model.UserProfile;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by sergeybutorin on 24.03.17.
 */
@SuppressWarnings("unused")
public interface AccountService {
    UserProfile addUser(@NotNull String login, @NotNull String email, @NotNull String password);
    UserProfile getUserByLogin(String login);
    List<UserProfile> getLeaders(int page);
    void updateRating(UserProfile newUser);
    void changeData(UserProfile newUser);
}
