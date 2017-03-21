package com.aerohockey.services;

import com.aerohockey.model.UserProfile;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * Created by sergeybutorin on 15.03.17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AccountServiceTest {
    @Autowired
    private JdbcTemplate template;
    private AccountService accountService;

    @Before
    public void setup(){
        accountService = new AccountService(template);
    }
    private UserProfile addUser(String name){
        final String defailtPassword = "123";
        final String defaultEmail = "foo@mail.ru";
        return accountService.addUser(name, defaultEmail, defailtPassword);
    }

    @Test
    public void testAddUserSimple(){
        final String login = "foo";
        final UserProfile testUser = addUser(login);
        assertNotNull(testUser);
        assertSame(login, testUser.getLogin());
    }

    @Test
    public void testAddUserConflict(){
        final String login = "foo";
        addUser(login);
        assertNull(addUser(login));
    }

    @Test
    public void testChangeEmail() {
        final String login = "userChangeEmail";
        final UserProfile testUser = addUser(login);
        final String newEmail = "newemail@mail.ru";
        testUser.setEmail(newEmail);
        accountService.changeData(testUser);
        assertSame(newEmail, testUser.getEmail());
    }

    @Test
    public void testUpdateRating() {
        final String login = "userUpdateRating";
        final UserProfile testUser = addUser(login);
        final int ratingValue = 10;
        testUser.changeRating(ratingValue); //increasing rating
        accountService.changeData(testUser);
        assertSame(ratingValue, testUser.getRating());
        testUser.changeRating(-ratingValue); // decreasing rating
        accountService.changeData(testUser);
        assertSame(0, testUser.getRating());
    }

    @Test
    public void testGetEmpty() {
        assertNull(accountService.getUserByLogin("empty"));
    }
}
