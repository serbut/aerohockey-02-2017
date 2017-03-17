package com.aerohockey.services;

import com.aerohockey.model.UserProfile;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by sergeybutorin on 15.03.17.
 */
public class AccountServiceTest {
    private AccountService accountService;
    private final String defaultLogin = "foo";
    private final String defaultEmail = "foo@mail.ru";
    private final String defailtPassword = "123";

    @Before
    public void setup(){
        accountService = new AccountService();
    }

    @Test
    public void checkAddUser(){
        final UserProfile testUser = accountService.addUser(defaultLogin, defaultEmail, defailtPassword);
        assertNotNull(testUser);
        assertSame(testUser, accountService.getUserByLogin(defaultLogin));
    }

    @Test
    public void checkChangeEmail() {
        final UserProfile testUser = accountService.addUser(defaultLogin, defaultEmail, defailtPassword);
        final String newEmail = "newemail@mail.ru";
        testUser.setEmail(newEmail);
        accountService.changeData(testUser);
        assertSame(newEmail, testUser.getEmail());
    }

    @Test
    public void checkUpdateRating() {
        final UserProfile testUser = accountService.addUser(defaultLogin, defaultEmail, defailtPassword);
        final int ratingValue = 10;
        testUser.changeRating(ratingValue); //increasing rating
        accountService.changeData(testUser);
        assertSame(ratingValue, testUser.getRating());
        testUser.changeRating(-ratingValue); // decreasing rating
        accountService.changeData(testUser);
        assertSame(0, testUser.getRating());
    }

    @Test
    public void checkLeaderboard() {
        final UserProfile testUser = accountService.addUser(defaultLogin, defaultEmail, defailtPassword);

    }
}
