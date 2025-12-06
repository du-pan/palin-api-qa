package com.palin.api.qa.request;

import com.palin.api.qa.model.ApiRequestParams;
import com.palin.api.qa.constant.enums.RequestMethod;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;

import static com.palin.api.qa.config.ApiClient.sendHttpRequest;
import static com.palin.api.qa.constant.enums.RequestMethod.*;

@Slf4j
public class SharedApiRequests {
  public void noBodyHttpRequest(final ApiRequestParams params) {
    final RequestMethod requestMethod = params.getRequestMethod();
    if (requestMethod.equals(POST) || requestMethod.equals(PUT) || requestMethod.equals(PATCH)) {
      log.error("Allowed RequestMethod types are: [{}] or [{}]", GET, DELETE);
      throw new IllegalArgumentException(
          "Not valid RequestMethod provided: [" + requestMethod + "]");
    }
    final Response httpResponse =
        sendHttpRequest(params.getApiEndpoint(), requestMethod, params.getAccessToken());

  }

  public void writeOpsHttpRequest(final ApiRequestParams params) {
    final RequestMethod requestMethod = params.getRequestMethod();
    if (requestMethod.equals(GET) || requestMethod.equals(DELETE)) {
      log.error("Allowed RequestMethod types are: [{}] or [{}] or [{}]", POST, PUT, DELETE);
      throw new IllegalArgumentException(
              "Not valid RequestMethod provided: [" + requestMethod + "]");
    }
  }
}
