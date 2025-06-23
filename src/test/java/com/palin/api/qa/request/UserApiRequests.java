package com.palin.api.qa.request;

import com.palin.api.qa.model.RequestMethod;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.Assert;

import static com.palin.api.qa.config.ApiClient.sendRequest;
import static com.palin.api.qa.constant.JsonPropertyConstants.*;
import static com.palin.api.qa.constant.TestConstants.API_USER_LOGIN_URL;
import static com.palin.api.qa.constant.TestConstants.APPLICATION_JSON;
import static com.palin.api.qa.model.RequestMethod.POST;
import static com.palin.api.qa.util.ObjectConverterUtil.getEntityJsonObject;
import static org.apache.http.HttpStatus.SC_OK;
import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

public class UserApiRequests {

  public String authorizeUser(final JSONObject body) {
    final Response authorizeUserResponse =
        sendRequest(API_USER_LOGIN_URL, POST, body, APPLICATION_JSON);
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
    final Response authorizeUserIncorrect = sendRequest(API_USER_LOGIN_URL, POST, bodyPath, APPLICATION_JSON);
    assertEquals(authorizeUserIncorrect.statusCode(), SC_UNAUTHORIZED);
  }

  private JSONObject constructAuthUserBody(final String username, final String password) {
    final JSONObject userLoginBody = new JSONObject();
    userLoginBody.put(USERNAME, username);
    userLoginBody.put(PASSWORD, password);
    assertNotNull(userLoginBody);

    return userLoginBody;
  }
}
