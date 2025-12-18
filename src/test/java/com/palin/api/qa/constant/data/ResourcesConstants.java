package com.palin.api.qa.constant.data;

public class ResourcesConstants {
  public static final String API_TEST_JSON_PATH = "src/test/resources/api/";
  // --- User auth --------------------------------------------------------------------------------
  public static final String USER_LOGIN_EMILY_JSON =
      API_TEST_JSON_PATH + "userAuth/userLoginEmily.json";
  public static final String USER_LOGIN_WRONG_USERNAME =
      API_TEST_JSON_PATH + "userAuth/userLoginWrongUsername.json";
  public static final String USER_LOGIN_WRONG_PASSWORD_JSON =
      API_TEST_JSON_PATH + "userAuth/userLoginWrongPassword.json";
  public static final String USER_LOGOUT_RESPONSE_BODY_JSON =
      API_TEST_JSON_PATH + "userAuth/userLogout.json";
  // ----------------------------------------------------------------------------------------------
  // ----------------------------------------------------------------------------------------------
  public static final String ADD_NEW_USER_JANE_DOE_JSON = API_TEST_JSON_PATH + "addUser/addNewUserJaneDoe.json";
  // ----------------------------------------------------------------------------------------------
}
