package com.palin.api.qa.request;

import com.palin.api.qa.model.ApiRequestParams;
import com.palin.api.qa.constant.enums.RequestMethod;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;

import static com.palin.api.qa.config.ApiClient.sendHttpRequest;
import static com.palin.api.qa.constant.enums.RequestMethod.*;
import static com.palin.api.qa.constant.main.JsonPropertyConstants.ROOT_QUERY;
import static com.palin.api.qa.constant.main.TestConstants.APPLICATION_JSON;
import static com.palin.api.qa.util.ApiRequestVerify.verifyHttpResponseContent;
import static com.palin.api.qa.util.ObjectConverterUtil.getEntityJsonObject;
import static org.apache.commons.lang3.ArrayUtils.EMPTY_STRING_ARRAY;
import static org.apache.http.HttpStatus.SC_BAD_REQUEST;

@Slf4j
public class SharedApiRequests {
  public void noBodyHttpRequest(final ApiRequestParams params) {
    final RequestMethod requestMethod = params.getRequestMethod();
    if (requestMethod.equals(POST) || requestMethod.equals(PUT) || requestMethod.equals(PATCH)) {
      log.error("Allowed RequestMethod types are: [{}] or [{}]", GET, DELETE);
      throw new IllegalArgumentException(
          "Not valid RequestMethod provided: [" + requestMethod + "]");
    }

    JSONObject expectedResponseBody = null;
    if (params.getExpectedResponsePath() != null) {
      expectedResponseBody = getEntityJsonObject(params.getExpectedResponsePath());
    } else if (params.getExpectedResponseJson() != null) {
      expectedResponseBody = params.getExpectedResponseJson();
    }

    String inJsonPath = ROOT_QUERY;
    if (params.getInJsonPath() != null) {
      inJsonPath = params.getInJsonPath();
    }

    String[] pathsToBeIgnored = EMPTY_STRING_ARRAY;
    if (params.getPathsToBeIgnored() != null) {
      pathsToBeIgnored = params.getPathsToBeIgnored();
    }

    final Response httpResponse =
        sendHttpRequest(params.getApiEndpoint(), requestMethod, params.getAccessToken());

    final int expectedHttpStatus = params.getExpectedHttpStatus();
    if (expectedHttpStatus >= SC_BAD_REQUEST) {
      verifyHttpResponseContent(httpResponse, expectedHttpStatus, expectedResponseBody);
    } else {
      verifyHttpResponseContent(
          httpResponse, expectedHttpStatus, expectedResponseBody, inJsonPath, pathsToBeIgnored);
    }
  }

  public void writeOpsHttpRequest(final ApiRequestParams params) {
    final RequestMethod requestMethod = params.getRequestMethod();
    if (requestMethod.equals(GET) || requestMethod.equals(DELETE)) {
      log.error("Allowed RequestMethod types are: [{}] or [{}] or [{}]", POST, PUT, DELETE);
      throw new IllegalArgumentException(
          "Not valid RequestMethod provided: [" + requestMethod + "]");
    }

    JSONObject body = new JSONObject();
    if (params.getBodyPath() != null) {
      body = getEntityJsonObject(params.getBodyPath());
    } else if (params.getBodyJson() != null) {
      body = params.getBodyJson();
    }

    JSONObject expectedResponseBody = null;
    if (params.getExpectedResponsePath() != null) {
      expectedResponseBody = getEntityJsonObject(params.getExpectedResponsePath());
    } else if (params.getExpectedResponseJson() != null) {
      expectedResponseBody = params.getExpectedResponseJson();
    }

    String inJsonPath = ROOT_QUERY;
    if (params.getInJsonPath() != null) {
      inJsonPath = params.getInJsonPath();
    }

    String[] pathsToBeIgnored = EMPTY_STRING_ARRAY;
    if (params.getPathsToBeIgnored() != null) {
      pathsToBeIgnored = params.getPathsToBeIgnored();
    }

    final Response httpResponse =
        sendHttpRequest(
            params.getApiEndpoint(),
            requestMethod,
            body,
            APPLICATION_JSON,
            params.getAccessToken());

    final int expectedHttpStatus = params.getExpectedHttpStatus();
    if (expectedHttpStatus >= SC_BAD_REQUEST) {
      verifyHttpResponseContent(httpResponse, expectedHttpStatus, expectedResponseBody);
    } else {
      verifyHttpResponseContent(
          httpResponse, expectedHttpStatus, expectedResponseBody, inJsonPath, pathsToBeIgnored);
    }
  }
}
