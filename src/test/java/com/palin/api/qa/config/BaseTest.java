package com.palin.api.qa.config;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeTest;

public class BaseTest {

  @BeforeTest(alwaysRun = true)
  public void setUp() {
    RestAssured.baseURI = "";
  }
}
