package com.aerohockey.services;

import com.aerohockey.model.UserProfile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Created by sergeybutorin on 24.03.17.
 */
@SuppressWarnings("unused")
public interface AccountService {
    @Nullable UserProfile addUser(@NotNull String login, @NotNull String email, @NotNull String password);
    @Nullable UserProfile getUserByLogin(String login);
    @Nullable UserProfile getUserById(Long id);
    List<UserProfile> getLeaders(int page);
    void updateRating(long id, int value);
    void changeData(@NotNull UserProfile newUser);
}
