package com.palin.api.qa.model;

import com.palin.api.qa.constant.enums.RequestMethod;
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
    private String inJsonPath;
    private String[] whenIgnoringPaths;
}
