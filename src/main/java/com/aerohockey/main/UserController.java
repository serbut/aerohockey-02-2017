package com.aerohockey.main;

import com.aerohockey.model.UserProfile;
import com.aerohockey.services.AccountService;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * Created by sergeybutorin on 20.02.17.
 */

@RestController
@CrossOrigin(origins = {"https://myfastball3.herokuapp.com", "http://localhost:3000", "http://127.0.0.1:3000"})
public class UserController {
    private final AccountService accountService;

    @Autowired
    public UserController(AccountService accountService) {
        this.accountService = accountService;
    }

    @RequestMapping(path = "/api/signup", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
    public ResponseEntity signup(@RequestBody GetUserRequest body, HttpSession httpSession) {
        final String login = body.getLogin();
        final String password = body.getPassword();
        final String email = body.getEmail();

        if (StringUtils.isEmpty(login)
                || StringUtils.isEmpty(password)
                || StringUtils.isEmpty(email)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("Wrong parameters"));
        }

        if (httpSession.getAttribute("userLogin") != null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponse("In this session user already logged in"));
        }

        final UserProfile existingUser = accountService.getUserByLogin(login);

        if (existingUser != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponse("User already exists"));
        }

        final UserProfile newUser = accountService.addUser(login, email, password);
        httpSession.setAttribute("userLogin", newUser.getLogin());
        return ResponseEntity.ok(new UserResponse(newUser.getId(), newUser.getLogin(), newUser.getEmail()));
    }

    @RequestMapping(path = "/api/login", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
    public ResponseEntity login(@RequestBody GetUserRequest body, HttpSession httpSession) {
        final String login = body.getLogin();
        final String password = body.getPassword();

        if (StringUtils.isEmpty(login)
                || StringUtils.isEmpty(password)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("Wrong parameters"));
        }

        if (httpSession.getAttribute("userLogin") != null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponse("In this session user already logged in"));
        }

        final UserProfile user = accountService.getUserByLogin(login);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("User doesn't exists"));
        }

        if (user.getPassword().equals(password) && user.getLogin().equals(login)) {
            httpSession.setAttribute("userLogin", user.getLogin());
            return ResponseEntity.ok(new UserResponse(user.getId(), user.getLogin(), user.getEmail()));
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse("Incorrect login/password"));
    }

    @RequestMapping(path = "/api/user", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity getCurrentUser(HttpSession httpSession) {
        final UserProfile user = accountService.getUserByLogin((String) httpSession.getAttribute("userLogin"));
        if (user == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponse("User not authorized"));
        } else {
            return ResponseEntity.ok(new UserResponse(user.getId(), user.getLogin(), user.getEmail()));
        }
    }

    @RequestMapping(path = "/api/change-password", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
    public ResponseEntity changePassword(@RequestBody GetUserRequest body, HttpSession httpSession) {
        final UserProfile user = accountService.getUserByLogin((String) httpSession.getAttribute("userLogin"));
        if (user == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponse("User not authorised"));
        }

        final String oldPassword = body.getOldPassword();
        final String newPassword = body.getPassword();

        if (StringUtils.isEmpty(oldPassword) || StringUtils.isEmpty(newPassword)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("Wrong parameters"));
        }

        if (!user.getPassword().equals(oldPassword)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponse("Incorrect old password"));
        }

        user.setPassword(newPassword);
        accountService.changeData(user);
        return ResponseEntity.ok(new UserResponse(user.getId(), user.getLogin(), user.getEmail()));

    }

    @RequestMapping(path = "/api/change-user-data", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
    public ResponseEntity changeUserData(@RequestBody GetUserRequest body, HttpSession httpSession) {
        final UserProfile user = accountService.getUserByLogin((String) httpSession.getAttribute("userLogin"));
        if (user == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponse("User not authorized"));
        } else {
            final String newEmail = body.getEmail();

            if (!StringUtils.isEmpty(newEmail)) {
                user.setEmail(newEmail);
            }

            accountService.changeData(user);
            return ResponseEntity.ok(new UserResponse(user.getId(), user.getLogin(), user.getEmail()));
        }
    }

    @RequestMapping(path = "/api/logout", method = RequestMethod.POST)
    public ResponseEntity logout(HttpSession httpSession) {
        if (httpSession.getAttribute("userLogin") != null) {
            httpSession.invalidate();
            return ResponseEntity.ok("User logged out");
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponse("User not authorized"));
    }

    private static final class GetUserRequest {
        @JsonProperty("login")
        private String login;
        @JsonProperty("old-password")
        private String oldPassword;
        @JsonProperty("password")
        private String password;
        @JsonProperty("email")
        private String email;

        @SuppressWarnings("unused")
        private GetUserRequest() {
        }

        @SuppressWarnings("unused")
        private GetUserRequest(String login, String oldPassword, String password, String email) {
            this.login = login;
            this.oldPassword = oldPassword;
            this.password = password;
            this.email = email;
        }

        public String getLogin() {
            return login;
        }

        public String getOldPassword() {
            return oldPassword;
        }

        public String getPassword() {
            return password;
        }

        public String getEmail() {
            return email;
        }
    }

    private static final class UserResponse {
        private final long id;
        private final String login;
        private final String email;

        UserResponse(long id, String login, String email) {
            this.id = id;
            this.login = login;
            this.email = email;
        }

        @SuppressWarnings("unused")
        public long getId() {
            return id;
        }

        @SuppressWarnings("unused")
        public String getLogin() {
            return login;
        }

        @SuppressWarnings("unused")
        public String getEmail() {
            return email;
        }
    }

    private static final class ErrorResponse {

        private final String error;

        ErrorResponse(String error) {
            this.error = error;
        }

        @SuppressWarnings("unused")
        public String getError() {
            return error;
        }
    }
}