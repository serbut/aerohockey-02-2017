package com.aerohockey.services;

import com.aerohockey.model.UserProfile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Created by sergeybutorin on 18.02.17.
 */

@Service
public class AccountServiceImpl implements AccountService{
    private final JdbcTemplate template;
    public static final int USERS_ON_PAGE = 10;
    private static final Logger LOGGER = LoggerFactory.getLogger(AccountServiceImpl.class);

    AccountServiceImpl(JdbcTemplate template) {
        this.template = template;
    }

    public void clear() {
        final String clearTable = "TRUNCATE TABLE users";
        template.execute(clearTable);
        LOGGER.info("Table users successfully cleared.");
    }

    @Override
    public @Nullable UserProfile addUser(@NotNull String login, @NotNull String email, @NotNull String password) {
        try {
            final String query = "INSERT INTO users (login, email, password) VALUES (?, ?, ?)";
            template.update(query, login, email, password);
        } catch (DuplicateKeyException e) {
            LOGGER.info("User with login {} already exists.", login);
            return null;
        } catch (DataAccessException e) {
            LOGGER.info(e.getLocalizedMessage());
            return null;
        }
        return getUserByLogin(login);
    }

    @Override
    public @Nullable UserProfile getUserByLogin(String login) {
        try {
            return template.queryForObject("SELECT * FROM users WHERE login = ?", USER_PROFILE_ROW_MAPPER, login);
        } catch (EmptyResultDataAccessException e) {
            LOGGER.info("User with login {} not found.", login);
            return null;
        } catch (DataAccessException e) {
            LOGGER.info(e.getLocalizedMessage());
            return null;
        }
    }

    @Override
    public @Nullable UserProfile getUserById(Long id) {
        try {
            return template.queryForObject("SELECT * FROM users WHERE id = ?", USER_PROFILE_ROW_MAPPER, id);
        } catch (EmptyResultDataAccessException e) {
            LOGGER.info("User with id = {} not found.", id);
            return null;
        } catch (DataAccessException e) {
            LOGGER.info(e.getLocalizedMessage());
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
