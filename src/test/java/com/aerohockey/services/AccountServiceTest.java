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

    @Before
    public void setup(){
        accountService = new AccountService();
    }

    public UserProfile addUser(String name){
        final String defailtPassword = "123";
        final String defaultEmail = "foo@mail.ru";
        return accountService.addUser(name, defaultEmail, defailtPassword);
    }

    @Test
    public void testAddUserSimple(){
        final UserProfile testUser = addUser(defaultLogin);
        assertNotNull(testUser);
        assertSame(testUser, accountService.getUserByLogin(defaultLogin));
    }

    @Test
    public void testAddUserConflict(){
        addUser(defaultLogin);
        assertNull(addUser(defaultLogin));
    }

    @Test
    public void testChangeEmail() {
        final UserProfile testUser = addUser(defaultLogin);
        final String newEmail = "newemail@mail.ru";
        testUser.setEmail(newEmail);
        accountService.changeData(testUser);
        assertSame(newEmail, testUser.getEmail());
    }

    @Test
    public void testUpdateRating() {
        final UserProfile testUser = addUser(defaultLogin);
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
