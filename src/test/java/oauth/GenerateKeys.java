package oauth;

import helpers.Credentials;
import io.restassured.RestAssured;
import io.restassured.authentication.FormAuthConfig;
import io.restassured.http.ContentType;
import io.restassured.http.Cookie;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.BeforeSuite;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.post;
import static io.restassured.http.ContentType.JSON;


public class GenerateKeys {
    WebDriver driver;
    Credentials cts = new Credentials();
    private static String url_request_token = "https://api.themoviedb.org/4/auth";
    private static String url_user_approve = "https://www.themoviedb.org/auth/access?request_token=";
    ChromeOptions chromeOptions = new ChromeOptions();
    public String access_token;
    public String token;


    public String generate_request_token() {

        Map<String, Object> jsonAsMap = new HashMap<>();
        jsonAsMap.put("redirect_to", "http://www.themoviedb.org");

        Response resp = RestAssured.given()
                .contentType(JSON)
                .accept(ContentType.JSON)
                .body(jsonAsMap)
                .auth()
                .oauth2(cts.getBearer())
                .post(url_request_token + "/request_token");

        Assert.assertEquals(resp.getStatusCode(), 200);
        JsonPath js = resp.jsonPath();
        String request_token = js.get("request_token");
        System.out.println(request_token);
        return request_token;
    }

    public void user_approve(String request_token) {
        chromeOptions.addArguments("--headless");
        driver = new ChromeDriver(chromeOptions);
        driver.get(url_user_approve + request_token);
        driver.manage().window().maximize();
        driver.findElement(By.xpath("//a[@href='/login']")).click();
        driver.findElement(By.id("username")).sendKeys(cts.getUsername());
        driver.findElement(By.id("password")).sendKeys(cts.getPassword());
        driver.findElement(By.xpath("//input[@value='Login']")).click();
        driver.findElement(By.name("submit")).click();
        driver.quit();
    }


    public String user_create_write_access(String request_token) {
        Map<String, Object> jsonAsMap = new HashMap<>();
        jsonAsMap.put("request_token", request_token);

        Response resp = RestAssured.given()
                .contentType(JSON)
                .accept(ContentType.JSON)
                .body(jsonAsMap)
                .auth()
                .oauth2(cts.getBearer())
                .post(url_request_token + "/access_token");

        Assert.assertEquals(resp.getStatusCode(), 200);
        JsonPath js = resp.jsonPath();
        String access_token = js.get("access_token");

        return access_token;

    }

    public String set_user_write_access() {
        token = generate_request_token();
        user_approve(token);
        access_token = user_create_write_access(token);
        return access_token;
    }


}
