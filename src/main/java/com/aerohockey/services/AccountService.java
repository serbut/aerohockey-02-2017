package com.aerohockey.services;

import com.aerohockey.model.UserProfile;
import com.sun.istack.internal.Nullable;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by sergeybutorin on 24.03.17.
 */
@SuppressWarnings("unused")
public interface AccountService {
    @Nullable UserProfile addUser(@NotNull String login, @NotNull String email, @NotNull String password);
    @Nullable UserProfile getUserByLogin(@NotNull String login);
    List<UserProfile> getLeaders(@NotNull int page);
    void updateRating(@NotNull UserProfile newUser);
    void changeData(@NotNull UserProfile newUser);
}
