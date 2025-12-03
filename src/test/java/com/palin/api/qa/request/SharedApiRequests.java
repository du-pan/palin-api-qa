package com.palin.api.qa.request;

import com.palin.api.qa.model.RequestMethod;
import io.restassured.response.Response;

import static com.palin.api.qa.config.ApiClient.sendHttpRequest;

public class SharedApiRequests {
  public void noBodyHttpRequest(
      final String endpoint, final RequestMethod requestMethod, final String accessToken) {
    final Response httpResponse = sendHttpRequest(endpoint, requestMethod, accessToken);
  }
}
