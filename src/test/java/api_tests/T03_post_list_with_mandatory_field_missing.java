package api_tests;

import helpers.Random_generator;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import oauth.GenerateKeys;
import org.testng.annotations.Test;
import resources.URL_resource_file;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.core.IsEqual.equalTo;

public class T03_post_list_with_mandatory_field_missing {

    GenerateKeys gk = new GenerateKeys();
    String write_access_token = gk.set_user_write_access();

    Random_generator rg = new Random_generator();
    String random_name = rg.generateRandomString();

    URL_resource_file url = new URL_resource_file();
    int id;

    @Test
    public void TC01_createList() {
        Map<String, Object> jsonAsMap = new HashMap<>();
        jsonAsMap.put("name", "QA_" + random_name);

        RestAssured.given().
                contentType(JSON).
                accept(ContentType.JSON).
                body(jsonAsMap).
                auth().
                oauth2(write_access_token).
                post(url.setupURL()).
                then().
                assertThat().
                statusCode(422).
                body("errors[0]", equalTo("iso_639_1 must be provided"));
    }

}
