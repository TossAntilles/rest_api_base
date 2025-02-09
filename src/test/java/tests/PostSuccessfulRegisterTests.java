package tests;

import org.junit.jupiter.api.Test;

import io.restassured.http.ContentType;
import static io.restassured.RestAssured.given;
import static org.hamcrest.core.Is.is;

public class PostSuccessfulRegisterTests {

    String requestUri = "https://reqres.in/api/register";


    @Test
    void successfulRegisterRequest(){
        String authData = "{\"email\": \"eve.holt@reqres.in\", \"password\": \"pistol\"}";
        given()
            .log().uri()
            .body(authData)
            .contentType(ContentType.JSON)
        .when()
            .post(requestUri)
        .then()
            .log().status()
            .log().body()
            .statusCode(200)
            .body("id", is(4))
            .body("token", is("QpwL5tke4Pnpja7X4"));
    }

    @Test
    void successfulRegisterRequestExternalData(){
        String authData = "{\"email\": \"janet.weaver@reqres.in\", \"password\": \"pistol\", \"hobby\": \"shooting\"}";
        given()
                .log().uri()
                .body(authData)
                .contentType(ContentType.JSON)
                .when()
                .post(requestUri)
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("id", is(2))
                .body("token", is("QpwL5tke4Pnpja7X2"));
    }

    @Test
    void unsuccessfulRegisterUnknownUser(){
        String authData = "{\"email\": \"tosst@reqres.in\", \"password\": \"pistol\"}";
        given()
                .log().uri()
                .body(authData)
                .contentType(ContentType.JSON)
                .when()
                .post(requestUri)
                .then()
                .log().status()
                .log().body()
                .statusCode(400)
                .body("error", is("Note: Only defined users succeed registration"));
    }

    @Test
    void unsuccessfulRegisterRequestNullPassword(){
        String authData = "{\"email\": \"eve.holt@reqres.in\", \"password\": \"\"}";
        given()
                .log().uri()
                .body(authData)
                .contentType(ContentType.JSON)
                .when()
                .post(requestUri)
                .then()
                .log().status()
                .log().body()
                .statusCode(400)
                .body("error", is("Missing password"));
    }

    @Test
    void unsuccessfulRegisterRequestMissingPassword(){
        String authData = "{\"email\": \"eve.holt@reqres.in\"}";
        given()
                .log().uri()
                .body(authData)
                .contentType(ContentType.JSON)
                .when()
                .post(requestUri)
                .then()
                .log().status()
                .log().body()
                .statusCode(400)
                .body("error", is("Missing password"));
    }

    @Test
    void unsuccessfulRegisterRequestMissingUsername(){
        String authData = "{\"password\": \"pistol\"}";
        given()
                .log().uri()
                .body(authData)
                .contentType(ContentType.JSON)
                .when()
                .post(requestUri)
                .then()
                .log().status()
                .log().body()
                .statusCode(400)
                .body("error", is("Missing email or username"));
    }

    @Test
    void unsuccessfulRegisterRequestMissingData(){
        String authData = "{}";
        given()
                .log().uri()
                .body(authData)
                .contentType(ContentType.JSON)
                .when()
                .post(requestUri)
                .then()
                .log().status()
                .log().body()
                .statusCode(400)
                .body("error", is("Missing email or username"));
    }


}
