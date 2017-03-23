package com.aerohockey.controller;

import com.aerohockey.model.UserProfile;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import static org.junit.Assert.*;
/**
 * Created by sergeybutorin on 16.03.17.
 */

@SpringBootTest(webEnvironment = RANDOM_PORT)
@RunWith(SpringRunner.class)
@Transactional
public class UserControllerTest {
    @Autowired
    private TestRestTemplate restTemplate;

    private final String defaultPassword = "123";
    private final String defaultEmail = "foo@mail.ru";

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

    private void changeData(List<String> cookie, String email, HttpStatus httpStatus) {
        final HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.put(HttpHeaders.COOKIE, cookie);
        final HttpEntity<UserProfile> requestEntity = new HttpEntity<>(new UserProfile(null, email, null), requestHeaders);

        final ResponseEntity<UserProfile> result = restTemplate.postForEntity("/api/change-user-data", requestEntity, UserProfile.class);
        assertEquals(httpStatus, result.getStatusCode());
        assertEquals(email, result.getBody().getEmail());
    }

    private void getCurrentUser(List<String> cookie, HttpStatus httpStatus){
        final HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.put(HttpHeaders.COOKIE, cookie);
        final HttpEntity requestEntity = new HttpEntity(requestHeaders);

        final ResponseEntity result = restTemplate.exchange("/api/user", HttpMethod.GET, requestEntity, UserProfile.class);
        assertEquals(httpStatus, result.getStatusCode());
    }

    @Test
    public void testSignup() {
        final String login = "testSignup";
        signup(login, "", defaultPassword, HttpStatus.BAD_REQUEST);
        signup("", defaultEmail, defaultPassword, HttpStatus.BAD_REQUEST);
        signup(login, defaultEmail, "", HttpStatus.BAD_REQUEST);

        final List<String> cookie = signup(login, defaultEmail, defaultPassword, HttpStatus.OK);
        logout(cookie, HttpStatus.OK);

        signup(login, "bar@mail.ru", defaultPassword, HttpStatus.FORBIDDEN); // try to create user with existing login
    }

    @Test
    public void logout() {
        final String login = "testLogout";
        final List<String> cookie = signup(login, defaultEmail, defaultPassword, HttpStatus.OK);

        logout(cookie, HttpStatus.OK);

        logout(cookie, HttpStatus.FORBIDDEN); //user already logged out
    }

    @Test
    public void testLogin() {
        final String login = "testLogin";
        login(login, "", HttpStatus.BAD_REQUEST);
        login("", defaultPassword, HttpStatus.BAD_REQUEST);

        List<String> cookie = signup(login, defaultEmail, defaultPassword, HttpStatus.OK);
        logout(cookie, HttpStatus.OK);

        cookie = login(login, defaultPassword, HttpStatus.OK);
//        login(login, defaultPassword, HttpStatus.FORBIDDEN);

        logout(cookie, HttpStatus.OK);

        login("notExist", defaultPassword, HttpStatus.FORBIDDEN); //login with incorrect login
        login(login, "incorrectPassword", HttpStatus.FORBIDDEN); //login with incorrect password
    }

    @Test
    public void testGetCurrentUser() {
        final String login = "testGetCurrentUser";
        final List<String> cookie = signup(login, defaultEmail, defaultPassword, HttpStatus.OK);

        getCurrentUser(cookie, HttpStatus.OK);
        logout(cookie, HttpStatus.OK);

        getCurrentUser(cookie, HttpStatus.FORBIDDEN);
    }

    @Test
    public void testChangePassword() {
        final String login = "testChangePassword";
        final String newPassword = "qwe";
        List<String> cookie = signup(login, defaultEmail, defaultPassword, HttpStatus.OK);

        changePassword(cookie, defaultPassword, newPassword, HttpStatus.OK);
        logout(cookie, HttpStatus.OK);

        login(login, defaultPassword, HttpStatus.FORBIDDEN); //try to login with old password

        cookie = login(login, newPassword, HttpStatus.OK); //login with new password

        changePassword(cookie, "wrongPassword", "password", HttpStatus.FORBIDDEN); //try to change password with incorrect old password

        logout(cookie, HttpStatus.OK);
    }

    @Test
    public void testChangeUserData() {
        final String login = "testChangeUserData";
        final String newEmail = "testChangeUserData@mail.ru";
        final List<String> cookie = signup(login, defaultEmail, defaultPassword, HttpStatus.OK);

        changeData(cookie, newEmail, HttpStatus.OK);
        logout(cookie, HttpStatus.OK);
    }

    @Test
    public void testLeaderboard() {
        //TODO
    }
}
