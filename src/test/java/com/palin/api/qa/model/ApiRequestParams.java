package com.palin.api.qa.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.json.JSONObject;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiRequestParams {
    private String apiEndpoint;
    private RequestMethod requestMethod;
    private String accessToken;
    private String bodyPath;
    private JSONObject bodyJson;
    private int expectedHttpStatus;
    private String expectedResponsePath;
    private JSONObject expectedResponseJson;
    private String jsonPath;
    private String[] whenIgnoringPaths;
}
