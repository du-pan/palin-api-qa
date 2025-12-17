package com.palin.api.qa.test;

import static com.palin.api.qa.constant.data.ResourcesConstants.ADD_NEW_USER_JANE_DOE_JSON;
import static com.palin.api.qa.constant.data.ResourcesConstants.USER_LOGIN_EMILY_JSON;
import static com.palin.api.qa.constant.main.TestConstants.API_USER_ADD_URL;
import static org.apache.http.HttpStatus.SC_CREATED;

import com.palin.api.qa.config.BaseTest;
import com.palin.api.qa.constant.enums.EntityType;
import com.palin.api.qa.constant.enums.RequestMethod;
import com.palin.api.qa.model.ApiRequestParams;
import com.palin.api.qa.request.UserApiRequests;
import org.testng.annotations.Test;

public class AddNewUserTest extends BaseTest {
  private final UserApiRequests userApiRequests = new UserApiRequests();

  @Test
  void shouldCreateNewUser() {
    userApiRequests.authorizeUser(USER_LOGIN_EMILY_JSON);
    userApiRequests.writeOpsHttpRequest(
        ApiRequestParams.builder()
            .entityTypeToBeDeleted(EntityType.USER)
            .accessToken(accessToken.get())
            .apiEndpoint(API_USER_ADD_URL)
            .requestMethod(RequestMethod.POST)
            .bodyPath(ADD_NEW_USER_JANE_DOE_JSON)
            .expectedHttpStatus(SC_CREATED)
            .build());
  }
}
