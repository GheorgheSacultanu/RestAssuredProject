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

import java.util.HashMap;
import java.util.Map;

import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.core.IsEqual.equalTo;


public class E2E_post_get_update_get_delete_list_with_items {

    GenerateKeys gk = new GenerateKeys();
    String write_access_token = gk.set_user_write_access();

    Random_generator rg = new Random_generator();
    String random_name = rg.generateRandomString();

    URL_resource_file url = new URL_resource_file();
    int id;
    String name_updated = "QA_name_updated";
    String description_updated = "Description_updated";

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
    public void T02_getList() {
        RestAssured.
                given().
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
    public void TC03_addItems() {
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
    public void T04_updateList() {
        Map<String, Object> jsonAsMap = new HashMap<>();
        jsonAsMap.put("name", name_updated);
        jsonAsMap.put("description", description_updated);

        RestAssured.given().
                contentType(JSON).
                accept(ContentType.JSON).
                body(jsonAsMap).
                auth().
                oauth2(write_access_token).
                put(url.setupURL() + "/" + id).
                then().
                assertThat().
                statusCode(201).
                body("status_message", equalTo("The item/record was updated successfully.")).
                body("status_code", equalTo(12)).
                body("success", equalTo(true));
    }

    @Test
    public void T05_getList() {
        RestAssured.
                given().
                auth().
                oauth2(write_access_token).
                get(url.setupURL() + "/" + id).
                then().
                assertThat().
                statusCode(200).
                body("id", equalTo(id)).
                body("name", equalTo(name_updated)).
                body("description", equalTo(description_updated));
    }

    @Test
    public void TC06_updateItems() {
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

    @Test
    public void TC07_getList_with_items() {
        RestAssured.
                given().
                auth().
                oauth2(write_access_token).
                get(url.setupURL() + "/" + id).
                then().
                assertThat().
                statusCode(200).
                body("results[0].media_type", equalTo("movie")).
                body("results[0].original_title", equalTo("Whiplash"));
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
