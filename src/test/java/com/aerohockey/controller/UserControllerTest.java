package com.aerohockey.controller;

import com.aerohockey.model.UserProfile;
import com.aerohockey.responses.LeaderboardResponse;
import com.aerohockey.responses.UserResponse;
import com.aerohockey.services.AccountServiceImpl;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static com.aerohockey.services.AccountServiceImpl.USERS_ON_PAGE;

import static org.junit.Assert.*;
/**
 * Created by sergeybutorin on 16.03.17.
 */

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@Transactional
public class UserControllerTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private AccountServiceImpl accountServiceImpl;

    final String defaultLogin = "user";
    private final String defaultPassword = "123";
    private final String defaultEmail = "foo@mail.ru";

    @After
    public void clear() {
        accountServiceImpl.clear();
    }

    private List<String> getCookie(ResponseEntity<UserProfile> result) {
        final List<String> cookie = result.getHeaders().get("Set-Cookie");
        assertNotNull(cookie);
        assertFalse(cookie.isEmpty());
        return cookie;
    }

    private List<String> signup(String login, String email, String password, HttpStatus httpStatus) {
        final ResponseEntity<UserProfile> result = restTemplate.postForEntity("/api/signup", new UserProfile(login, email, password), UserProfile.class);
        assertEquals(httpStatus, result.getStatusCode());

        if(result.getStatusCode() == HttpStatus.OK) {
            assertEquals(login, result.getBody().getLogin());
        }

        return getCookie(result);
    }

    private List<String> login(String login, String password, HttpStatus httpStatus) {
        final ResponseEntity<UserProfile> result = restTemplate.postForEntity("/api/login", new UserProfile(login, "", password), UserProfile.class);
        assertEquals(httpStatus, result.getStatusCode());

        if(result.getStatusCode() == HttpStatus.OK) {
            assertEquals(login, result.getBody().getLogin());
        }

        return getCookie(result);
    }

    private void logout(List<String> cookie, HttpStatus httpStatus) {
        final HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.put(HttpHeaders.COOKIE, cookie);
        final HttpEntity requestEntity = new HttpEntity(requestHeaders);

        final ResponseEntity result = restTemplate.postForEntity("/api/logout", requestEntity, String.class);
        assertEquals(httpStatus, result.getStatusCode());
    }

    private void changePassword(List<String> cookie, String oldPassword, String password, HttpStatus httpStatus) {
        final HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.put(HttpHeaders.COOKIE, cookie);
        final HttpEntity<UserProfile> requestEntity = new HttpEntity<>(new UserProfile(oldPassword, password), requestHeaders);

        final ResponseEntity result = restTemplate.postForEntity("/api/change-password", requestEntity, UserProfile.class);
        assertEquals(httpStatus, result.getStatusCode());
    }

    private void changeData(List<String> cookie, String email) {
        final HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.put(HttpHeaders.COOKIE, cookie);
        final HttpEntity<UserProfile> requestEntity = new HttpEntity<>(new UserProfile(null, email, null), requestHeaders);

        final ResponseEntity<UserProfile> result = restTemplate.postForEntity("/api/change-user-data", requestEntity, UserProfile.class);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(email, result.getBody().getEmail());
    }

    private void getCurrentUser(List<String> cookie, String login, HttpStatus httpStatus){
        final HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.put(HttpHeaders.COOKIE, cookie);
        final HttpEntity requestEntity = new HttpEntity(requestHeaders);

        final ResponseEntity<UserProfile> result = restTemplate.exchange("/api/user", HttpMethod.GET, requestEntity, UserProfile.class);
        assertEquals(httpStatus, result.getStatusCode());
        if(result.getStatusCode() == HttpStatus.OK) {
            assertEquals(login, result.getBody().getLogin());
        }
    }

    private void getLeaderboard(List<String> userLogins) {
        final HttpEntity requestEntity = new HttpEntity(null);
        for (int i = 0; i < Math.ceil(userLogins.size() / USERS_ON_PAGE); i++) {
            final ResponseEntity<LeaderboardResponse> result = restTemplate.exchange("/api/leaderboard?page={page}",
                    HttpMethod.GET,
                    requestEntity,
                    LeaderboardResponse.class,
                    i + 1);
            assertEquals(HttpStatus.OK, result.getStatusCode());

            final List<UserResponse> resultUsers = result.getBody().getUsers();
            for (int j = 0; j < resultUsers.size(); j++) {
                assertEquals(userLogins.get(j + i * USERS_ON_PAGE), resultUsers.get(j).getLogin());
            }
        }
    }

    public String generateRandomString(int length) {
        final char[] chars = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        final StringBuilder sb = new StringBuilder();
        final Random random = new Random();
        for (int i = 0; i < length; i++) {
            final char c = chars[random.nextInt(chars.length)];
            sb.append(c);
        }
        return sb.toString();
    }

    @Test
    public void testSignup() {
        signup(defaultLogin, "", defaultPassword, HttpStatus.BAD_REQUEST);
        signup("", defaultEmail, defaultPassword, HttpStatus.BAD_REQUEST);
        signup(defaultLogin, defaultEmail, "", HttpStatus.BAD_REQUEST);

        final List<String> cookie = signup(defaultLogin, defaultEmail, defaultPassword, HttpStatus.OK);
        logout(cookie, HttpStatus.OK);

        signup(defaultLogin, "bar@mail.ru", defaultPassword, HttpStatus.FORBIDDEN); // try to create user with existing login
    }

    @Test
    public void logout() {
        final List<String> cookie = signup(defaultLogin, defaultEmail, defaultPassword, HttpStatus.OK);

        logout(cookie, HttpStatus.OK);

        logout(cookie, HttpStatus.FORBIDDEN); //user already logged out
    }

    @Test
    public void testLogin() {
        login(defaultLogin, "", HttpStatus.BAD_REQUEST);
        login("", defaultPassword, HttpStatus.BAD_REQUEST);

        List<String> cookie = signup(defaultLogin, defaultEmail, defaultPassword, HttpStatus.OK);
        logout(cookie, HttpStatus.OK);

        cookie = login(defaultLogin, defaultPassword, HttpStatus.OK);

        logout(cookie, HttpStatus.OK);

        login("notExist", defaultPassword, HttpStatus.FORBIDDEN); //login with incorrect login
        login(defaultLogin, "incorrectPassword", HttpStatus.FORBIDDEN); //login with incorrect password
    }

    @Test
    public void testGetCurrentUser() {
        final List<String> cookie = signup(defaultLogin, defaultEmail, defaultPassword, HttpStatus.OK);

        getCurrentUser(cookie, defaultLogin, HttpStatus.OK);
        logout(cookie, HttpStatus.OK);

        getCurrentUser(cookie, defaultLogin, HttpStatus.FORBIDDEN);
    }

    @Test
    public void testChangePassword() {
        final String newPassword = "qwe";
        List<String> cookie = signup(defaultLogin, defaultEmail, defaultPassword, HttpStatus.OK);

        changePassword(cookie, defaultPassword, newPassword, HttpStatus.OK);
        logout(cookie, HttpStatus.OK);

        login(defaultLogin, defaultPassword, HttpStatus.FORBIDDEN); //try to login with old password

        cookie = login(defaultLogin, newPassword, HttpStatus.OK); //login with new password

        changePassword(cookie, "wrongPassword", "password", HttpStatus.FORBIDDEN); //try to change password with incorrect old password

        logout(cookie, HttpStatus.OK);
    }

    @Test
    public void testChangeUserData() {
        final String newEmail = "testChangeUserData@mail.ru";
        final List<String> cookie = signup(defaultLogin, defaultEmail, defaultPassword, HttpStatus.OK);

        changeData(cookie, newEmail);
        logout(cookie, HttpStatus.OK);
    }

    @Test
    public void testLeaderboard() {
        final List<String> userLogins = new ArrayList<>();
        final int usersCount = 25;
        for (int i = 0; i < usersCount; i++) {
            final String login = generateRandomString(10);
            final List<String> cookie = signup(login, login + "@mail.ru", "123", HttpStatus.OK);
            logout(cookie, HttpStatus.OK);
            userLogins.add(login);
        }
        Collections.sort(userLogins);
        getLeaderboard(userLogins);
    }
}
