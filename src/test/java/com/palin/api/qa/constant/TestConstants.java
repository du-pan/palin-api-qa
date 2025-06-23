package com.palin.api.qa.constant;

public class TestConstants {
    // --- API URLs -----------------------------------------------------------------------
    public static final String API_AUTH_QUERY_PARAM = "Authorization";
    public static final String API_BEARER_TOKEN_AUTH = "Bearer %s";
    public static final String APPLICATION_JSON = "application/json";
    public static final String API_USER_LOGIN_URL = "/users/v1/auth";
    // ------------------------------------------------------------------------------------
    // --- API Constants ------------------------------------------------------------------
    public static final String NOT_VALID_ACCESS_TOKEN =
            "eyJraWQiOiJabEpqaGx2Nm4ra2VWNWJ3U2NPV1NWM2NIT1JJa3RCXC9HQ2N6azhyRFpOND0iLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiIzM2Y0YTg";
    public static final String WRONG_USERNAME = "wrong-username@mail.com";
    public static final String WRONG_PASSWORD = "wrong-password";
    public static final String INCORRECT_LOGIN_MSG = "Incorrect username or password.";
    // ------------------------------------------------------------------------------------
}
