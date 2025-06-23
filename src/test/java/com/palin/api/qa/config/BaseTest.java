package com.palin.api.qa.config;

import io.restassured.RestAssured;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.BeforeTest;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import static com.palin.api.qa.config.ApiClient.getApiCapabilities;

@Slf4j
public class BaseTest {
  public static String TEMP_DOWNLOAD_DIR;

  @BeforeTest(alwaysRun = true)
  public void setUp() {
    final Properties props = new Properties();
    try {
      log.info("Load kinto-esg-qa.properties file.");
      final FileReader reader = new FileReader("src/test/resources/kinto-esg-qa.properties");
      props.load(reader);
      TEMP_DOWNLOAD_DIR = new File(props.getProperty("downloadDir")).getAbsolutePath();
    } catch (IOException e) {
      log.error("IOException detected. Driver quit.");
      log.error("Properties file could not be loaded.");
      log.error(e.getMessage());
    }

    RestAssured.baseURI = props.getProperty("apiBaseUrl");
    getApiCapabilities();
  }
}
