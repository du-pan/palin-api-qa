package com.palin.api.qa.util;

import com.palin.api.qa.config.ApiClient;
import io.restassured.response.Response;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.palin.api.qa.util.ObjectConverterUtil.getEntityJsonObject;
import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static net.javacrumbs.jsonunit.core.Option.*;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

public class ApiHelper extends ApiClient {
  public void assertResponseHttpStatus(final Response response, final int expectedHttpStatus) {
    assertNotNull(response);
    assertEquals(
        response.statusCode(),
        expectedHttpStatus,
        "Unexpected HTTP status for response:\n" + response.body().asPrettyString());
  }

  public static void assertResponseBodyEquals(
      final Response response, final JSONObject expectedJsonBody) {
    assertThatJsonEquals(response, expectedJsonBody);
  }

  public static void assertResponseBodyEquals(
      final Response response, final JSONObject... expectedJsonBody) {
    AssertionError assertionError;
    for (JSONObject jsonObject : expectedJsonBody) {
      try {
        assertThatJsonEquals(response, jsonObject);
        return;
      } catch (AssertionError e) {
        assertionError = e;
        assertionError.printStackTrace();
      }
      throw new AssertionError(
          "None of expected bodies are equal as expected. Assertion message: ["
              + assertionError.getMessage()
              + "]");
    }
  }

  public static void assertResponseBodyEquals(
      final Response response, final String... expectedBodyPath) {
    JSONObject[] jsonArray = new JSONObject[expectedBodyPath.length];
    ArrayList<JSONObject> expectedJsonObjects = new ArrayList<>();
    for (String bodyPath : expectedBodyPath) {
      expectedJsonObjects.add(getEntityJsonObject(bodyPath));
    }

    assertResponseBodyEquals(response, expectedJsonObjects.toArray(jsonArray));
  }

  public static void assertResponseBodyEquals(
      final Response response,
      final JSONObject expectedJsonBody,
      final String jsonPath,
      final String... pathsToBeIgnored) {
    assertThatJson(responseToJsonObject(response))
        .when(
            TREATING_NULL_AS_ABSENT,
            IGNORING_ARRAY_ORDER,
            REPORTING_DIFFERENCE_AS_NORMALIZED_STRING)
        .whenIgnoringPaths(pathsToBeIgnored)
        .inPath(jsonPath)
        .isEqualTo(expectedJsonBody);
  }

  private static void assertThatJsonEquals(final Response response, final JSONObject expectedJson) {
    assertThatJson(responseToJsonObject(response))
        .when(
            TREATING_NULL_AS_ABSENT,
            IGNORING_ARRAY_ORDER,
            IGNORING_EXTRA_FIELDS,
            REPORTING_DIFFERENCE_AS_NORMALIZED_STRING)
        .isEqualTo(expectedJson);
  }

  private static JSONObject responseToJsonObject(final Response response) {
    return new JSONObject(response.body().asPrettyString());
  }
}
