## Automated test project for API testing purposes
## REST Assured as test library: https://rest-assured.io/
## Testing of DUMMY JSON endpoints: https://dummyjson.com/
#### This is just a PoC project. It can be done much better and organised to improve its scalability and reusability.

### Requirements:
- JAVA JDK 21
- JAVA_HOME set as Environmental variable
- JAVA_HOME/bin set as Path
- Apache Maven download: https://maven.apache.org/download.cgi?.
- Apache downloaded folder as M2_HOME Environmental variable
- M2_HOME/bin set as Path

### Test run:
- Play button next to the TestNG test name
- Or using Maven command:

  This will run REST Assured test:
  ```
  mvn clean test -Dtest=UserLoginTest
  ```
  Separated test scenario can be run isolated:
  ```
  mvn clean test -Dtest=TestClassName#testName
  ```
  ```
  mvn clean test -Dtest=UserLoginTest#shouldNotLoginUserWrongParams
  ```

  This is my most recommendation if testing has to be automated and delivered fast.
  API testing with UNIT testing can be real deal and good first layer of quality assurance.
  Later, e2e testing of web or mobile applications can be added to cover main functionalities on the UI.

