package com.palin.api.qa.test;

import com.palin.api.qa.config.BaseTest;
import com.palin.api.qa.request.UserApiRequests;
import org.testng.annotations.Test;

import static com.palin.api.qa.constant.ResourcesConstants.*;
import static com.palin.api.qa.constant.TestConstants.PASSWORD_MICHAEL;
import static com.palin.api.qa.constant.TestConstants.USERNAME_MICHAEL;

public class UserLoginTest extends BaseTest {
  private static final UserApiRequests userApiRequests = new UserApiRequests();

  @Test
  void shouldLoginUserJsonPath() {
    userApiRequests.authorizeUser(USER_LOGIN_EMILY_JSON);
  }

  @Test
  void shouldLoginUserParameters() {
    userApiRequests.authorizeUser(USERNAME_MICHAEL, PASSWORD_MICHAEL);
  }

  @Test
  void shouldNotLoginUserWrongParams() {
    userApiRequests.authorizeUserIncorrect(USER_LOGIN_WRONG_USERNAME);
    userApiRequests.authorizeUserIncorrect(USER_LOGIN_WRONG_PASSWORD_JSON);
  }
}
