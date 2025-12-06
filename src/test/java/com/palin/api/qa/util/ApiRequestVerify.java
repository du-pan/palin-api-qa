package com.palin.api.qa.util;

import io.restassured.response.Response;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.palin.api.qa.util.ObjectConverterUtil.getEntityJsonObject;

public class ApiRequestVerify extends ApiHelper {
  public void verifyHttpResponseContent(
      final Response response,
      final int expectedHttpStatus,
      final JSONObject... expectedResponseBody) {
    assertResponseHttpStatus(response, expectedHttpStatus);
    if (expectedResponseBody.length > 0) {
      assertResponseBodyEquals(response, expectedResponseBody);
    }
  }

  public void verifyHttpResponseContent(
      final Response response, final int expectedHttpStatus, final String... expectedResponseBody) {
    verifyHttpResponseContent(
        response, expectedHttpStatus, convertStringArrToJsonArr(expectedResponseBody));
  }

  private static JSONObject[] convertStringArrToJsonArr(final String[] stringArr) {
    List<JSONObject> jsonObjectList = new ArrayList<>();
    Arrays.stream(stringArr)
        .toList()
        .forEach(body -> jsonObjectList.add(getEntityJsonObject(body)));

    return jsonObjectList.toArray(JSONObject[]::new);
  }
}
