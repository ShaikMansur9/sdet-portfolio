package com.mansur.api;

import com.mansur.listeners.TestExecutionListener;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;

@Listeners(TestExecutionListener.class)
public class PostsApiTest {

    private RequestSpecification spec;

    @BeforeClass
    public void setUp() {
        spec = new RequestSpecBuilder()
                .setBaseUri("https://jsonplaceholder.typicode.com")
                .setContentType("application/json")
                .build();
    }

    @Test
    public void getPostReturnsExpectedPost() {

        given()
                .spec(spec)

                .when()
                .get("/posts/1")

                .then()
                .statusCode(200)
                .body("id", equalTo(1))
                .body("userId", equalTo(1))
                .body("title", notNullValue())
                .body("body", notNullValue());
    }

    @Test
    public void getAllPostsReturnsHundredPosts() {

        given()
                .spec(spec)

                .when()
                .get("/posts")

                .then()
                .statusCode(200)
                .body("$", hasSize(100))
                .body("[0].id", equalTo(1));
    }

    @Test
    public void createPostReturnsCreatedStatus() {

        String requestBody = """
                {
                    "title": "SDET Automation",
                    "body": "Learning REST Assured API testing",
                    "userId": 1
                }
                """;

        given()
                .spec(spec)
                .body(requestBody)

                .when()
                .post("/posts")

                .then()
                .statusCode(201)
                .body("title", equalTo("SDET Automation"))
                .body("body", equalTo("Learning REST Assured API testing"))
                .body("userId", equalTo(1))
                .body("id", notNullValue());
    }

    @Test
    public void unknownPostReturnsNotFound() {

        given()
                .spec(spec)

                .when()
                .get("/posts/99999")

                .then()
                .statusCode(404);
    }
}