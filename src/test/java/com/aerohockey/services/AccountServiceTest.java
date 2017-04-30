package com.aerohockey.services;

import com.aerohockey.model.UserProfile;
import org.jetbrains.annotations.Nullable;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by sergeybutorin on 15.03.17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class AccountServiceTest {
    @Autowired
    private JdbcTemplate template;

    private AccountServiceImpl accountServiceImpl;

    final String defaultLogin = "user-test";
    final String defailtPassword = "123";
    final String defaultEmail = "user@mail.ru";

    @Before
    public void setup() {
        accountServiceImpl = new AccountServiceImpl(template);
    }

    private @Nullable UserProfile addUser(String login){
        return accountServiceImpl.addUser(login, defaultEmail, defailtPassword);
    }

    @Test
    public void testAddUserSimple(){
        final UserProfile testUser = addUser(defaultLogin);
        assertNotNull(testUser);
        assertEquals(defaultLogin, testUser.getLogin());
    }

    @Test
    public void testAddUserConflict(){
        addUser(defaultLogin);
        assertNull(addUser(defaultLogin));
    }

    @Test
    public void testChangeEmail() {
        UserProfile testUser = addUser(defaultLogin);
        assertNotNull(testUser);
        final String newEmail = "newemail@mail.ru";
        testUser.setEmail(newEmail);
        accountServiceImpl.changeData(testUser);
        testUser = accountServiceImpl.getUserByLogin(testUser.getLogin());
        assertNotNull(testUser);
        assertEquals(newEmail, testUser.getEmail());
    }

    @Test
    public void testUpdateRating() {
        UserProfile testUser = addUser(defaultLogin);
        assertNotNull(testUser);
        final int ratingValue = 10;
        testUser.changeRating(ratingValue); //increasing rating
        accountServiceImpl.updateRating(testUser.getId(), ratingValue);
        testUser = accountServiceImpl.getUserByLogin(testUser.getLogin());
        assertNotNull(testUser);
        assertSame(ratingValue, testUser.getRating());

        testUser.changeRating(-ratingValue); // decreasing rating
        accountServiceImpl.updateRating(testUser.getId(), ratingValue);
        testUser = accountServiceImpl.getUserByLogin(testUser.getLogin());
        assertNotNull(testUser);
        assertSame(0, testUser.getRating());
    }

    @Test
    public void testGetEmpty() {
        assertNull(accountServiceImpl.getUserByLogin("empty"));
    }

    @Test
    public void testGetLeaders() {
        UserProfile user = addUser("user3");
        assertNotNull(user);
        int ratingValue = 10;
        user.changeRating(ratingValue);
        accountServiceImpl.updateRating(user.getId(), ratingValue);

        final List<UserProfile> users = new ArrayList<>();
        users.add(user);

        user = addUser("user2");
        assertNotNull(user);
        ratingValue = 5;
        user.changeRating(ratingValue);
        accountServiceImpl.updateRating(user.getId(), ratingValue);
        users.add(user);

        user = addUser("user1");
        assertNotNull(user);
        ratingValue = 1;
        user.changeRating(ratingValue);
        accountServiceImpl.updateRating(user.getId(), ratingValue);
        users.add(user);

        final List<UserProfile> leaders = accountServiceImpl.getLeaders(1);

        for(int i = 0; i < users.size(); i++){
            assertEquals(users.get(i).getLogin(), leaders.get(i).getLogin());
        }
    }
}
