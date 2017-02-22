package api.model;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by sergeybutorin on 20.02.17.
 */

public class UserProfile {
    private static final AtomicLong ID_GENERATOR = new AtomicLong(0);
    private final long id;
    private String login;
    private String email;
    private String password;

    public UserProfile(String login, String email, String password) {
        this.id = ID_GENERATOR.getAndIncrement();
        this.login = login;
        this.email = email;
        this.password = password;
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
    public String getPassword() {
        return password;
    }

    @SuppressWarnings("unused")
    public String getEmail() {
        return email;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
