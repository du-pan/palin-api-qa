package com.palin.api.qa.util;

import io.restassured.response.Response;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.palin.api.qa.util.ObjectConverterUtil.getEntityJsonObject;
import static org.apache.http.HttpStatus.SC_BAD_REQUEST;

public class ApiRequestVerify extends ApiHelper {
  public static void verifyHttpResponseContent(
      final Response response,
      final int expectedHttpStatus,
      final JSONObject... expectedResponseBody) {
    assertResponseHttpStatus(response, expectedHttpStatus);
    if (expectedResponseBody.length > 0) {
      assertResponseBodyEquals(response, expectedResponseBody);
    }
  }

  public static void verifyHttpResponseContent(
      final Response response, final int expectedHttpStatus, final String... expectedResponseBody) {
    verifyHttpResponseContent(
        response, expectedHttpStatus, convertStringArrToJsonArr(expectedResponseBody));
  }

  public static void verifyHttpResponseContent(
      final Response response,
      final int expectedHttpStatus,
      final JSONObject expectedJsonBody,
      final String inJsonPath,
      final String... pathsToBeIgnored) {
    assertResponseHttpStatus(response, expectedHttpStatus);
    if (expectedHttpStatus >= SC_BAD_REQUEST) {
      assertResponseBodyEquals(response, expectedJsonBody);
    } else if (expectedJsonBody != null) {
      assertResponseBodyEquals(response, expectedJsonBody, inJsonPath, pathsToBeIgnored);
    }
  }

  public static void verifyHttpResponseContent(
      final Response response,
      final int expectedHttpStatus,
      final String expectedBodyPath,
      final String inJsonPath,
      final String... pathsToBeIgnored) {
    verifyHttpResponseContent(
        response,
        expectedHttpStatus,
        getEntityJsonObject(expectedBodyPath),
        inJsonPath,
        pathsToBeIgnored);
  }

  private static JSONObject[] convertStringArrToJsonArr(final String[] stringArr) {
    List<JSONObject> jsonObjectList = new ArrayList<>();
    Arrays.stream(stringArr)
        .toList()
        .forEach(body -> jsonObjectList.add(getEntityJsonObject(body)));

    return jsonObjectList.toArray(JSONObject[]::new);
  }
}
