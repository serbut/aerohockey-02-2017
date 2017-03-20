package com.aerohockey.services;

import com.aerohockey.model.UserProfile;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sergeybutorin on 18.02.17.
 */

@Service
public class AccountService {
    private final Map<String, UserProfile> userNameToUserProfile = new HashMap<>();

    private final JdbcTemplate template;
    AccountService(JdbcTemplate template) {
        this.template = template;
    }

    public UserProfile addUser(@NotNull String login, @NotNull String email, @NotNull String password) {
        final UserProfile user = new UserProfile(login, email, password);
        try {
            final String query = "INSERT INTO users (login, email, password) VALUES (?, ?, ?)";
            template.update(query, login, email, password);
        }
        catch (DuplicateKeyException e) {
            return null;
        }
        return user;
    }

    public UserProfile getUserByLogin(String login) {
        try {
            return template.queryForObject("SELECT * FROM users WHERE login = ?", userMapper, login);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<UserProfile> getLeaders() {
        final List<UserProfile> leaders = new ArrayList<>();
        for (UserProfile user : userNameToUserProfile.values()) {
            leaders.add(user);
        }
        return leaders;
    }

    public void changeData(UserProfile newUser) {
        final String query = "UPDATE users SET " +
                "email = COALESCE (?, email), " +
                "password = COALESCE (?, password)" +
                "WHERE login = ?";
        template.update(query, newUser.getEmail(), newUser.getPassword(), newUser.getLogin());
    }

    private static final RowMapper<UserProfile> userMapper = (rs, rowNum) -> {
        final int id = rs.getInt("id");
        final String login = rs.getString("login");
        final String email = rs.getString("email");
        final String password = rs.getString("password");
        final int rating = rs.getInt("rating");
        return new UserProfile(id, login, email, password, rating);
    };
}
