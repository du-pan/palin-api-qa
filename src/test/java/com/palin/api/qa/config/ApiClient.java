package com.palin.api.qa.config;

import static com.palin.api.qa.constant.enums.RequestMethod.*;
import static com.palin.api.qa.constant.main.TestConstants.API_AUTH_QUERY_PARAM;
import static com.palin.api.qa.constant.main.TestConstants.API_BEARER_TOKEN_AUTH;
import static com.palin.api.qa.util.ObjectConverterUtil.getEntityJsonObject;
import static org.apache.http.HttpVersion.HTTP;
import static org.awaitility.Awaitility.waitAtMost;

import com.palin.api.qa.constant.enums.RequestMethod;
import io.restassured.RestAssured;
import io.restassured.config.ConnectionConfig;
import io.restassured.config.LogConfig;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;

@Slf4j
public class ApiClient {
  public static void getApiCapabilities() {
    log.info("API testing environment is selected.");
    RestAssured.config =
        RestAssured.config()
            .connectionConfig(
                ConnectionConfig.connectionConfig()
                    .closeIdleConnectionsAfterEachResponseAfter(60, TimeUnit.SECONDS))
            .logConfig(
                LogConfig.logConfig()
                    .enablePrettyPrinting(true)
                    .enableLoggingOfRequestAndResponseIfValidationFails()
                    .enableLoggingOfRequestAndResponseIfValidationFails());
  }

  public static Response sendHttpRequest(
      final String endpoint,
      final RequestMethod requestMethod,
      final String body,
      final String contentType,
      final String token) {
    if (requestMethod == null
        || StringUtils.isEmpty(endpoint)
        || (requestMethod != GET
            && requestMethod != POST
            && requestMethod != PUT
            && requestMethod != PATCH
            && requestMethod != DELETE)) {
      throw new IllegalArgumentException("Request method or endpoint URI cannot be empty or null.");
    }
    RequestSpecification request = RestAssured.given();

    if (token != null) {
      request = request.header(API_AUTH_QUERY_PARAM, String.format(API_BEARER_TOKEN_AUTH, token));
    }

    if (body != null) {
      log.info("Sending request body\n[{}]", body);
      assert !contentType.isEmpty();
      if (!getEntityJsonObject(body).isEmpty()) {
        request = request.body(getEntityJsonObject(body).toString()).contentType(contentType);
      } else {
        request = request.body(body).contentType(contentType);
      }
    }

    final Response response =
        switch (requestMethod) {
          case GET -> request.get(endpoint);
          case POST -> request.post(endpoint);
          case PUT -> request.put(endpoint);
          case PATCH -> request.patch(endpoint);
          case DELETE -> request.delete(endpoint);
        };
    log.info("Sending [{}] request to [{}] endpoint.", requestMethod, endpoint);
    waitAtMost(30, TimeUnit.SECONDS).until(() -> response.getStatusLine().contains(HTTP));
    log.info("[{}] request sent successfully.", requestMethod);

    return response.then().extract().response();
  }

  public static Response sendHttpRequest(final String endpoint, final RequestMethod requestMethod) {
    return sendHttpRequest(endpoint, requestMethod, (String) null, null, null);
  }

  public static Response sendHttpRequest(
      final String endpoint, final RequestMethod requestMethod, final String token) {
    return sendHttpRequest(endpoint, requestMethod, (String) null, null, token);
  }

  public static Response sendHttpRequest(
      final String endpoint,
      final RequestMethod requestMethod,
      final String body,
      final String contentType) {
    return sendHttpRequest(endpoint, requestMethod, body, contentType, null);
  }

  public static Response sendHttpRequest(
      final String endpoint,
      final RequestMethod requestMethod,
      final JSONObject body,
      final String contentType) {
    assert body != null;
    return sendHttpRequest(endpoint, requestMethod, body.toString(), contentType, null);
  }

  public static Response sendHttpRequest(
      final String endpoint,
      final RequestMethod requestMethod,
      final JSONObject body,
      final String contentType,
      final String token) {
    return sendHttpRequest(endpoint, requestMethod, body.toString(), contentType, token);
  }
}
