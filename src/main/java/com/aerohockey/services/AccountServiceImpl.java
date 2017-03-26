package com.aerohockey.services;

import com.aerohockey.model.UserProfile;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by sergeybutorin on 18.02.17.
 */

@Service
public class AccountServiceImpl implements AccountService{
    private final JdbcTemplate template;
    public static final int USERS_ON_PAGE = 10;
    AccountServiceImpl(JdbcTemplate template) {
        this.template = template;
    }

    public void clear() {
        final String clearTable = "TRUNCATE TABLE users";
        template.execute(clearTable);
    }

    @Override
    public UserProfile addUser(@NotNull String login, @NotNull String email, @NotNull String password) {
        try {
            final String query = "INSERT INTO users (login, email, password) VALUES (?, ?, ?)";
            template.update(query, login, email, password);
        }
        catch (DuplicateKeyException e) {
            return null;
        }
        return getUserByLogin(login);
    }

    @Override
    public UserProfile getUserByLogin(@NotNull String login) {
        try {
            return template.queryForObject("SELECT * FROM users WHERE login = ?", USER_PROFILE_ROW_MAPPER, login);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<UserProfile> getLeaders(int page) {
        final String query = "SELECT * FROM users " +
                "ORDER BY rating DESC, login ASC " +
                " LIMIT ? OFFSET ?";
        return template.query(query, USER_PROFILE_ROW_MAPPER, USERS_ON_PAGE, USERS_ON_PAGE * (page - 1));
    }

    @Override
    public void updateRating(@NotNull UserProfile newUser) {
        final String query = "UPDATE users SET " +
                "rating = COALESCE (?, rating) " +
                "WHERE login = ?";
        template.update(query, newUser.getRating(), newUser.getLogin());
    }

    @Override
    public void changeData(@NotNull UserProfile newUser) {
        final String query = "UPDATE users SET " +
                "email = COALESCE (?, email), " +
                "password = COALESCE (?, password) " +
                "WHERE login = ?";
        template.update(query, newUser.getEmail(), newUser.getPassword(), newUser.getLogin());
    }

    private static final RowMapper<UserProfile> USER_PROFILE_ROW_MAPPER = (rs, rowNum) -> {
        final int id = rs.getInt("id");
        final String login = rs.getString("login");
        final String email = rs.getString("email");
        final String password = rs.getString("password");
        final int rating = rs.getInt("rating");
        return new UserProfile(id, login, email, password, rating);
    };
}
