package com.aerohockey.services;

import com.aerohockey.model.UserProfile;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

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
    private UserProfile addUser(String login){
        final String defailtPassword = "123";
        final String defaultEmail = "foo@mail.ru";
        return accountService.addUser(login, defaultEmail, defailtPassword);
    }

    @Test
    public void testAddUserSimple(){
        final String login = "userSimple";
        final UserProfile testUser = addUser(login);
        assertNotNull(testUser);
        assertSame(login, testUser.getLogin());
    }

    @Test
    public void testAddUserConflict(){
        final String login = "userConflict";
        addUser(login);
        assertNull(addUser(login));
    }

    @Test
    public void testChangeEmail() {
        final UserProfile testUser = addUser("userChangeEmail");
        final String newEmail = "newemail@mail.ru";
        testUser.setEmail(newEmail);
        accountService.changeData(testUser);
        assertSame(newEmail, testUser.getEmail());
    }

    @Test
    public void testUpdateRating() {
        final UserProfile testUser = addUser("userUpdateRating");
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

    @Test
    public void testGetLeaders() {
        UserProfile user = addUser("user1");
        user.changeRating(10);
        accountService.updateRating(user);

        final List<UserProfile> users = new ArrayList<>();
        users.add(user);

        user = addUser("user2");
        user.changeRating(5);
        accountService.updateRating(user);
        users.add(user);

        user = addUser("user3");
        user.changeRating(1);
        accountService.updateRating(user);
        users.add(user);

        final List<UserProfile> leaders = accountService.getLeaders(users.size(), 1);

        for(int i = 0; i < users.size(); i++){
            assertEquals(users.get(i).getLogin(), leaders.get(i).getLogin());
        }
    }
}
