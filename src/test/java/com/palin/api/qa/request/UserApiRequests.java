package com.palin.api.qa.request;

import static com.palin.api.qa.config.ApiClient.sendHttpRequest;
import static com.palin.api.qa.config.BaseTest.accessToken;
import static com.palin.api.qa.constant.enums.RequestMethod.DELETE;
import static com.palin.api.qa.constant.enums.RequestMethod.POST;
import static com.palin.api.qa.constant.main.JsonPropertyConstants.*;
import static com.palin.api.qa.constant.main.TestConstants.*;
import static com.palin.api.qa.util.ApiHelper.assertResponseHttpStatus;
import static com.palin.api.qa.util.ObjectConverterUtil.getEntityJsonObject;
import static org.apache.http.HttpStatus.*;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import com.palin.api.qa.model.ApiRequestParams;
import io.restassured.response.Response;
import org.json.JSONObject;

public class UserApiRequests extends BaseApiRequests {

  public String authorizeUser(final JSONObject body) {
    final Response authUserResponse =
        sendHttpRequest(API_USERS_AUTH_LOGIN_URL, POST, body, APPLICATION_JSON);
    assertResponseHttpStatus(authUserResponse, SC_OK);

    final String sessionAccessToken = authUserResponse.jsonPath().getString(ACCESS_TOKEN);
    accessToken.set(sessionAccessToken);

    return sessionAccessToken;
  }

  public String authorizeUser(final String bodyPath) {
    return authorizeUser(getEntityJsonObject(bodyPath));
  }

  public String authorizeUser(final String username, final String password) {
    return authorizeUser(constructAuthUserBody(username, password));
  }

  public void authorizeUserIncorrect(final String bodyPath) {
    final Response incorrectUserAuthResponse =
        sendHttpRequest(API_USERS_AUTH_LOGIN_URL, POST, bodyPath, APPLICATION_JSON);
    assertEquals(incorrectUserAuthResponse.statusCode(), SC_BAD_REQUEST);
  }

  public void signOutUser(final String accessToken) {
    writeOpsHttpRequest(ApiRequestParams.builder()
            .accessToken(accessToken)
            .apiEndpoint(API_USERS_AUTH_LOGOUT_URL)
            .requestMethod(POST)
            .build());
   /* final Response signOutUserResponse =
        sendHttpRequest(
            API_USERS_AUTH_LOGOUT_URL, POST, (String) null, APPLICATION_JSON, accessToken);
    System.out.println(">>>>>>>>>>>>>>>>>>>>>>>> " + signOutUserResponse.body().asPrettyString());
    verifyHttpResponseContent(signOutUserResponse, SC_OK, USER_LOGOUT_RESPONSE_BODY_JSON);*/
  }

  public void deleteUserById(final String accessToken, final String userId) {
    noBodyHttpRequest(
        ApiRequestParams.builder()
            .accessToken(accessToken)
            .apiEndpoint(String.format(API_USERS_BY_ID_URL, userId))
            .requestMethod(DELETE)
            .expectedHttpStatus(SC_NOT_FOUND) // not found because mock DB
            .build());
  }

  private JSONObject constructAuthUserBody(final String username, final String password) {
    final JSONObject userLoginBody = new JSONObject();
    userLoginBody.put(USERNAME, username);
    userLoginBody.put(PASSWORD, password);
    assertNotNull(userLoginBody);

    return userLoginBody;
  }
}
