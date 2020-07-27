package api_tests;

import helpers.Random_generator;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import oauth.GenerateKeys;
import org.testng.annotations.Test;
import pojo.Post_request_schema_list;
import resources.URL_resource_file;

import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.core.IsEqual.equalTo;


public class E2E_post_getWithParams_delete_get_list {

    GenerateKeys gk = new GenerateKeys();
    String write_access_token = gk.set_user_write_access();

    Random_generator rg = new Random_generator();
    String random_name = rg.generateRandomString();

    URL_resource_file url = new URL_resource_file();
    int id;

    @Test
    public void TC01_createList() {
        Post_request_schema_list prs = new Post_request_schema_list(
                "QA_" + random_name, "fr", "Description_" + random_name, true, "test");

        Response resp = RestAssured.given().
                contentType(JSON).
                accept(ContentType.JSON).
                body(prs).
                auth().
                oauth2(write_access_token).
                post(url.setupURL()).
                then().
                assertThat().
                statusCode(201).
                body("status_message", equalTo("The item/record was created successfully.")).
                body("status_code", equalTo(1)).
                body("success", equalTo(true)).
                and().
                extract().
                response();
        id = resp.path("id");
    }

    @Test
    public void TC02_getList_with_param_api_key_and_page() {
        RestAssured.
                given().
                queryParam("api_key", "").
                queryParam("page", 2).
                auth().
                oauth2(write_access_token).
                get(url.setupURL() + "/" + id).
                then().
                assertThat().
                statusCode(200).
                body("id", equalTo(id)).
                body("name", equalTo("QA_" + random_name));
    }

    @Test
    public void TC03_deleteList() {
        RestAssured.given()
                .auth()
                .oauth2(write_access_token)
                .delete(url.setupURL() + "/" + id).
                then().
                assertThat().
                statusCode(200).
                body("status_message", equalTo("The item/record was deleted successfully."));
    }

    @Test
    public void TC04_getList_after_delete() {
        RestAssured.
                given().
                auth().
                oauth2(write_access_token).
                get(url.setupURL() + "/" + id).
                then().
                assertThat().
                statusCode(404).
                body("status_code", equalTo(34)).
                body("status_message", equalTo("The resource you requested could not be found."));
    }

}
