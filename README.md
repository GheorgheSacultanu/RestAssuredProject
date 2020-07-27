# Java + Rest Assured


## Pre-requistes

1) Download and Install -> Maven (https://maven.apache.org/install.html)
2) Download and Install -> Java  (https://www.java.com/en/download/)
3) Verify which Chrome Browser version you have and download the chrome driver version from provided link and unzip it into the same folder where is located the git repository.

How to check you chrome driver version check here -> https://www.whatismybrowser.com/

The chrome driver download form here -> http://chromedriver.storage.googleapis.com/index.html


## Provide Credentials

In order to use your user/test user please provide the username, password, and the API Read Access Token (v4 auth) on this file -> src/test/java/helpers/Credentials.java using the same format.
To get the API Read Access Token please check on your user settings page -> https://www.themoviedb.org/settings/api

In case you don't have an API Token, please proceed with clicking the button to generate one.



## Usage
To execute the tests you will need to be inside the git folder and after run the following command.

```bash
mvn verify
```

## Reporting
To check the test execution report please open inside the browser the html file which is available under this path -> target/surefire-reports/API_TEST_RESULTS_REPORT/Sample Test.html
