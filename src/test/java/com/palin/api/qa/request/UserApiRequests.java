package com.palin.api.qa.request;

import static com.palin.api.qa.constant.enums.RequestMethod.POST;
import static com.palin.api.qa.constant.main.JsonPropertyConstants.*;
import static com.palin.api.qa.constant.main.TestConstants.API_USER_LOGIN_URL;
import static com.palin.api.qa.constant.main.TestConstants.APPLICATION_JSON;
import static com.palin.api.qa.util.ObjectConverterUtil.getEntityJsonObject;
import static org.apache.http.HttpStatus.*;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import com.palin.api.qa.config.ApiClient;
import io.restassured.response.Response;
import org.json.JSONObject;

public class UserApiRequests {

  public String authorizeUser(final JSONObject body) {
    final Response authorizeUserResponse =
        ApiClient.sendHttpRequest(API_USER_LOGIN_URL, POST, body, APPLICATION_JSON);
    assertEquals(authorizeUserResponse.statusCode(), SC_OK);

    return authorizeUserResponse.jsonPath().getString(ACCESS_TOKEN);
  }

  public String authorizeUser(final String bodyPath) {
    return authorizeUser(getEntityJsonObject(bodyPath));
  }

  public String authorizeUser(final String username, final String password) {
    return authorizeUser(constructAuthUserBody(username, password));
  }

  public void authorizeUserIncorrect(final String bodyPath) {
    final Response authorizeUserIncorrect = ApiClient.sendHttpRequest(API_USER_LOGIN_URL, POST, bodyPath, APPLICATION_JSON);
    assertEquals(authorizeUserIncorrect.statusCode(), SC_BAD_REQUEST);
  }

  private JSONObject constructAuthUserBody(final String username, final String password) {
    final JSONObject userLoginBody = new JSONObject();
    userLoginBody.put(USERNAME, username);
    userLoginBody.put(PASSWORD, password);
    assertNotNull(userLoginBody);

    return userLoginBody;
  }
}
