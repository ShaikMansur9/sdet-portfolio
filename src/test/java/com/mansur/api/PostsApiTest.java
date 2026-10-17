package com.mansur.api;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

public class PostsApiTest {

    private RequestSpecification spec;

    @BeforeClass
    public void setUp() {
        spec = new RequestSpecBuilder()
                .setBaseUri("https://jsonplaceholder.typicode.com")
                .build();
    }

    @Test
    public void getPostReturnsExpectedPost() {
        given().spec(spec)
                .when().get("/posts/1")
                .then().statusCode(200)
                .body("id", equalTo(1));
    }

    @Test
    public void getAllPostsReturnsHundredPosts() {
        given().spec(spec)
                .when().get("/posts")
                .then().statusCode(200)
                .body("$", hasSize(100));
    }

    @Test
    public void createPostReturnsCreatedStatus() {
        String body = """
                {"title": "foo", "body": "bar", "userId": 1}
                """;

        given().spec(spec)
                .header("Content-Type", "application/json")
                .body(body)
                .when().post("/posts")
                .then().statusCode(201)
                .body("title", equalTo("foo"));
    }

    @Test
    public void unknownPostReturnsNotFound() {
        given().spec(spec)
                .when().get("/posts/99999")
                .then().statusCode(404);
    }
}