package com.aerohockey.responses;

public class ErrorResponse {
    public static final String WRONG_PARAMETERS = "Wrong parameters";
    public static final String NOT_AUTHORIZED = "User not authorized";
    public static final String INCORRECT_DATA = "Incorrect login/password";
    public static final String LOGIN_ALREADY_EXISTS = "User with such login already exists";
    public static final String SESSION_BUSY = "User with such login already exists";

    private final String error;

    public ErrorResponse(String error) {
        this.error = error;
    }

    @SuppressWarnings("unused")
    public String getError() {
        return error;
    }
}