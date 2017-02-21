package api.main;

import api.model.UserProfile;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import api.services.AccountService;
import javax.servlet.http.HttpSession;

/**
 * Created by sergeybutorin on 20.02.17.
 */

@RestController
public class RegistrationController {
    private final AccountService accountService;

    @Autowired
    public RegistrationController(AccountService accountService) {
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
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Wrong parameters");
        }
        final UserProfile existingUser = accountService.getUser(login);
        if (existingUser != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("User already exists");
        }

        final UserProfile newUser = accountService.register(login, email, password);
        final String sessionId = httpSession.getId();
        httpSession.setAttribute(sessionId, newUser.getId());
        return ResponseEntity.status(HttpStatus.OK).body("User " + login + " successfully registered. ID = " + newUser.getId());
    }

    @RequestMapping(path = "/api/login", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
    public ResponseEntity login(@RequestBody GetUserRequest body, HttpSession httpSession) {
        final String login = body.getLogin();
        final String password = body.getPassword();
        if (StringUtils.isEmpty(login)
                || StringUtils.isEmpty(password)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Wrong parameters");
        }
        final UserProfile user = accountService.getUser(login);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User doesn't exists");
        }
        if(user.getPassword().equals(password)){
            final String sessionId = httpSession.getId();
            httpSession.setAttribute(sessionId, user.getId());
            return ResponseEntity.status(HttpStatus.OK).body("User " + login + " successfully logged in");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Incorrect login/password");
    }

    @RequestMapping(path = "/api/user", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity getUserName(HttpSession httpSession) {
        final String login = (String) httpSession.getAttribute("login");
        if (login == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User not authorized");
        } else {
            return ResponseEntity.ok("Current user: " + login);
        }
    }

    @RequestMapping(path = "/api/logout", method = RequestMethod.POST)
    public HttpStatus logout(HttpSession httpSession) {
        httpSession.removeAttribute(httpSession.getId());
        return HttpStatus.OK;
    }

    private static final class GetUserRequest {
        @JsonProperty("login")
        private String login;
        @JsonProperty("password")
        private String password;
        @JsonProperty("email")
        private String email;

        @SuppressWarnings("unused")
        private GetUserRequest() {

        }

        @SuppressWarnings("unused")
        private GetUserRequest(String login, String password, String email) {
            this.login = login;
            this.password = password;
            this.email = email;
        }

        public String getLogin() {
            return login;
        }

        public String getPassword() {
            return password;
        }

        public String getEmail() {
            return email;
        }
    }
}
