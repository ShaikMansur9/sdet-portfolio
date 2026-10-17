package com.mansur.api;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class PostsApiTest {

    @Test
    public void getPostReturnsExpectedPost() {

        given()
                .baseUri("https://jsonplaceholder.typicode.com")
                .when()
                .get("/posts/1")
                .then()
                .statusCode(200)
                .body("id", equalTo(1));
    }
}