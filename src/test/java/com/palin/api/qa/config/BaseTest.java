package com.palin.api.qa.config;

import static com.palin.api.qa.config.ApiClient.getApiCapabilities;
import static com.palin.api.qa.util.ApiHelper.apiCleanUserData;

import com.palin.api.qa.util.ObservableValue;
import io.restassured.RestAssured;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;

@Slf4j
public abstract class BaseTest {
  public static String TEMP_DOWNLOAD_DIR;
  public static ObservableValue<String> accessToken = new ObservableValue<>();
  public static ObservableValue<String> createdUserId = new ObservableValue<>();
  public static ObservableValue<String> createdProductId = new ObservableValue<>();
  public static final List<Pair<String, String>> userCleanUpList = new ArrayList<>();
  public static final List<Pair<String, String>> productCleanUpList = new ArrayList<>();

  @BeforeTest(alwaysRun = true)
  public void setUp() {
    final Properties props = new Properties();
    try {
      log.info("Load palin-api-qa.properties file.");
      final FileReader reader = new FileReader("src/test/resources/palin-api-qa.properties");
      props.load(reader);
      TEMP_DOWNLOAD_DIR = new File(props.getProperty("downloadDir")).getAbsolutePath();
    } catch (IOException e) {
      log.error("IOException detected. Driver quit.");
      log.error("Properties file could not be loaded.");
      log.error(e.getMessage());
    }

    log.info("Setting up REST Assured base URI to [{}]", props.getProperty("apiBaseUrl"));
    RestAssured.baseURI = props.getProperty("apiBaseUrl");
    getApiCapabilities();
  }

  @AfterMethod(alwaysRun = true)
  public void userLogout() {
    log.info("Logout current user and clear test data.");
    apiCleanUserData();
  }
}
