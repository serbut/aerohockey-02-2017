package com.aerohockey.controller;

import com.aerohockey.model.UserProfile;
import com.aerohockey.services.AccountService;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by sergeybutorin on 20.02.17.
 */

@SuppressWarnings("unchecked")
@RestController
@CrossOrigin(origins = {"https://myfastball3.herokuapp.com", "http://localhost:3000", "http://127.0.0.1:3000"})
public class UserController {
    private final AccountService accountService;
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    public UserController(AccountService accountService) {
        this.accountService = accountService;
    }

    @RequestMapping(path = "/api/signup", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
    public ResponseEntity signup(@RequestBody UserProfile body, HttpSession httpSession) {
        final String login = body.getLogin();
        final String password = body.getPassword();
        final String email = body.getEmail();

        if (StringUtils.isEmpty(login)
                || StringUtils.isEmpty(password)
                || StringUtils.isEmpty(email)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse("Wrong parameters"));
        }

        if (httpSession.getAttribute("userLogin") != null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse("In this session user already logged in"));
        }

        final UserProfile newUser = accountService.addUser(login, email, password);
        if (newUser == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse("User already exists"));
        }
        httpSession.setAttribute("userLogin", newUser.getLogin());
        LOGGER.info("User {} registered", login);
        return ResponseEntity.ok(userResponse(newUser));
    }

    @RequestMapping(path = "/api/login", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
    public ResponseEntity login(@RequestBody UserProfile body, HttpSession httpSession) {
        final String login = body.getLogin();
        final String password = body.getPassword();

        if (StringUtils.isEmpty(login)
                || StringUtils.isEmpty(password)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse("Wrong parameters"));
        }

        if (httpSession.getAttribute("userLogin") != null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse("In this session user already logged in"));
        }

        final UserProfile user = accountService.getUserByLogin(login);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse("Incorrect login/password"));
        }

        if (user.getPassword().equals(password) && user.getLogin().equals(login)) {
            httpSession.setAttribute("userLogin", user.getLogin());
            LOGGER.info("User {} logged in", login);
            return ResponseEntity.ok(userResponse(user));
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse("Incorrect login/password"));
    }

    @RequestMapping(path = "/api/user", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity getCurrentUser(HttpSession httpSession) {
        final UserProfile user = accountService.getUserByLogin((String) httpSession.getAttribute("userLogin"));
        if (user == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse("User not authorized"));
        } else {
            return ResponseEntity.ok(userResponse(user));
        }
    }

    @RequestMapping(path = "/api/leaderboard", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<String> getLeadearboard() {
        final List<UserProfile> users = accountService.getLeaders();
        return ResponseEntity.ok(leaderboardResponse(users).toJSONString());
    }

    @RequestMapping(path = "/api/change-password", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
    public ResponseEntity changePassword(@RequestBody UserProfile body, HttpSession httpSession) {
        final UserProfile user = accountService.getUserByLogin((String) httpSession.getAttribute("userLogin"));
        if (user == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse("User not authorised"));
        }

        final String oldPassword = body.getOldPassword();
        final String newPassword = body.getPassword();

        if (StringUtils.isEmpty(oldPassword) || StringUtils.isEmpty(newPassword)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse("Wrong parameters"));
        }

        if (!user.getPassword().equals(oldPassword)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse("Incorrect old password"));
        }

        user.setPassword(newPassword);
        accountService.changeData(user);
        LOGGER.info("Password for user {} successfully changed.", httpSession.getAttribute("userLogin"));
        return ResponseEntity.ok(userResponse(user));
    }

    @RequestMapping(path = "/api/change-user-data", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
    public ResponseEntity changeUserData(@RequestBody UserProfile body, HttpSession httpSession) {
        final UserProfile user = accountService.getUserByLogin((String) httpSession.getAttribute("userLogin"));
        if (user == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse("User not authorized"));
        } else {
            final String newEmail = body.getEmail();

            if (!StringUtils.isEmpty(newEmail)) {
                user.setEmail(newEmail);
            }

            accountService.changeData(user);
            LOGGER.info("User data for user {} successfully changed.", httpSession.getAttribute("userLogin"));
            return ResponseEntity.ok(userResponse(user));
        }
    }

    @RequestMapping(path = "/api/logout", method = RequestMethod.POST)
    public ResponseEntity logout(HttpSession httpSession) {
        if (httpSession.getAttribute("userLogin") != null) {
            LOGGER.info("User {} logged out", httpSession.getAttribute("userLogin"));
            httpSession.invalidate();
            return ResponseEntity.ok("");
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse("User not authorized"));
    }

    private static String errorResponse(String errorMsg) {
        final JSONObject userDetailsJson = new JSONObject();
        userDetailsJson.put("error", errorMsg);
        return userDetailsJson.toJSONString();
    }

    private static JSONObject userResponse(UserProfile userProfile) {
        final JSONObject userDetailsJson = new JSONObject();
        userDetailsJson.put("id", userProfile.getId());
        userDetailsJson.put("login", userProfile.getLogin());
        userDetailsJson.put("email", userProfile.getEmail());
        userDetailsJson.put("rating", userProfile.getRating());
        return userDetailsJson;
    }

    private JSONArray leaderboardResponse(List<UserProfile> users) {
        final JSONArray jsonArray = new JSONArray();
        for(UserProfile u : users) {
            jsonArray.add(userResponse(u));
        }
        return jsonArray;
    }
}
