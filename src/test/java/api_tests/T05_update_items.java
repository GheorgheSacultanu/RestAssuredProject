package api_tests;


import com.google.gson.Gson;
import helpers.ItemWrapper;
import helpers.Random_generator;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import oauth.GenerateKeys;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;
import pojo.Item;
import pojo.Post_request_schema_list;
import resources.URL_resource_file;

import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.core.IsEqual.equalTo;


public class T05_update_items {

    GenerateKeys gk = new GenerateKeys();
    String write_access_token = gk.set_user_write_access();
    Random_generator rg = new Random_generator();
    String random_name = rg.generateRandomString();

    URL_resource_file url = new URL_resource_file();
    int id;

    @Test
    public void T01_createList() {
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
    public void TC02_addItems() {
        Item item = new Item();
        item.setMediaId(244786);
        item.setMediaType("movie");

        Item item2 = new Item();
        item2.setMediaId(500);
        item2.setMediaType("movie");


        ItemWrapper itemWrapper = new ItemWrapper();
        itemWrapper.addItem(item);
        itemWrapper.addItem(item2);
        Gson gson = new Gson();
        String body = gson.toJson(itemWrapper);
        RestAssured.given().
                contentType(JSON).
                accept(JSON).
                body(body).
                auth().
                oauth2(write_access_token).
                post(url.setupURL() + "/" + id + "/items").
                then().
                assertThat().
                statusCode(200).
                body("status_message", equalTo("Success."));
    }

    @Test
    public void TC03_updateItems() {
        Item item = new Item();
        item.setMediaId(244786);
        item.setMediaType("movie");
        item.setComment("The best movie");



        ItemWrapper itemWrapper = new ItemWrapper();
        itemWrapper.addItem(item);
        Gson gson = new Gson();
        String body = gson.toJson(itemWrapper);
        RestAssured.given().
                contentType(JSON).
                accept(JSON).
                body(body).
                auth().
                oauth2(write_access_token).
                put(url.setupURL() + "/" + id + "/items").
                then().
                assertThat().
                statusCode(200).
                body("status_message", equalTo("Success.")).
                body("status_code", equalTo(1)).
                body("success", equalTo(true));
    }

    @AfterTest
    public void deleteList() {
        RestAssured.given()
                .auth()
                .oauth2(write_access_token)
                .delete(url.setupURL() + "/" + id).
                then().
                assertThat().
                statusCode(200).
                body("status_message", equalTo("The item/record was deleted successfully."));
    }
}
