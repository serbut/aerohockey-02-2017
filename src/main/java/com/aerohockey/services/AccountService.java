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
    @Nullable UserProfile getUserByLogin(@NotNull String login);
    List<UserProfile> getLeaders(int page);
    void updateRating(@NotNull UserProfile newUser);
    void changeData(@NotNull UserProfile newUser);
}
