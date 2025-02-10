package tests;


import io.qameta.allure.restassured.AllureRestAssured;
import models.register.RegisterRequest;
import models.register.RegisterRequestMoreFields;
import models.register.SuccessfulRegisterResponse;
import models.register.UnsuccessfulRegisterResponse;
import org.junit.jupiter.api.Test;

import io.restassured.http.ContentType;

import static helpers.CustomAllureListener.withCustomTemplates;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PostSuccessfulRegisterTests {

    String requestUri = "https://reqres.in/api/register";

    @Test
    void successfulRegisterRequest(){
        RegisterRequest registerData = new RegisterRequest();
            registerData.setEmail("eve.holt@reqres.in");
            registerData.setPassword("pistol");

        SuccessfulRegisterResponse response =
            step("Sending request with correct email and password", () ->
                given()
                    .filter(withCustomTemplates())
                    .log().uri()
                    .log().headers()
                    .log().body()
                    .body(registerData)
                    .contentType(ContentType.JSON)
                .when()
                    .post(requestUri)
                .then()
                    .log().status()
                    .log().body()
                    .statusCode(200)
                    .extract().as(SuccessfulRegisterResponse.class)
        );

        step("Verify correct response", () -> {
            assertEquals("4", response.getId());
            assertEquals("QpwL5tke4Pnpja7X4", response.getToken());
        });

    }

    @Test
    void successfulRegisterRequestAdditionalData(){
        RegisterRequestMoreFields registerData = new RegisterRequestMoreFields();
        registerData.setEmail("janet.weaver@reqres.in");
        registerData.setPassword("pistol");
        registerData.setUserpic("Major.jpg");
        registerData.setSignature("Bzzzzz");

        SuccessfulRegisterResponse response =
            step("Sending request with correct email and password and other fields", () ->
                given()
                    .filter(withCustomTemplates())
                    .log().uri()
                    .log().headers()
                    .log().body()
                    .body(registerData)
                    .contentType(ContentType.JSON)
                .when()
                    .post(requestUri)
                .then()
                    .log().status()
                    .log().body()
                    .statusCode(200)
                    .extract().as(SuccessfulRegisterResponse.class)
            );

        step("Verify correct response", () -> {
            assertEquals("2", response.getId());
            assertEquals("QpwL5tke4Pnpja7X2", response.getToken());
        });
    }

    @Test
    void unsuccessfulRegisterUnknownUser(){
        RegisterRequest registerData = new RegisterRequest();
        registerData.setEmail("tosst@reqres.in");
        registerData.setPassword("pistol");

        UnsuccessfulRegisterResponse response =
            step("Sending request with unexpected email", () ->
                given()
                    .filter(withCustomTemplates())
                    .log().uri()
                    .log().headers()
                    .log().body()
                    .body(registerData)
                    .contentType(ContentType.JSON)
                .when()
                    .post(requestUri)
                .then()
                    .log().status()
                    .log().body()
                    .statusCode(400)
                    .extract().as(UnsuccessfulRegisterResponse.class)
            );

        step("Verify response, status code 400", () -> {
            assertEquals("Note: Only defined users succeed registration", response.getError());
        });
    }

    @Test
    void unsuccessfulRegisterRequestNullPassword(){
        RegisterRequest registerData = new RegisterRequest();
        registerData.setEmail("eve.holt@reqres.in");
        registerData.setPassword("");

        UnsuccessfulRegisterResponse response =
            step("Sending request with empty password", () ->
                given()
                    .filter(withCustomTemplates())
                    .log().uri()
                    .log().headers()
                    .log().body()
                    .body(registerData)
                    .contentType(ContentType.JSON)
                .when()
                    .post(requestUri)
                .then()
                    .log().status()
                    .log().body()
                    .statusCode(400)
                    .extract().as(UnsuccessfulRegisterResponse.class)
            );

        step("Verify response, status code 400", () -> {
            assertEquals("Missing password", response.getError());
        });
    }

    @Test
    void unsuccessfulRegisterRequestMissingPassword(){
        RegisterRequest registerData = new RegisterRequest();
        registerData.setEmail("eve.holt@reqres.in");

        UnsuccessfulRegisterResponse response =
            step("Sending request without password", () ->
                given()
                    .filter(withCustomTemplates())
                    .log().uri()
                    .log().headers()
                    .log().body()
                    .body(registerData)
                    .contentType(ContentType.JSON)
                .when()
                    .post(requestUri)
                .then()
                    .log().status()
                    .log().body()
                    .statusCode(400)
                    .extract().as(UnsuccessfulRegisterResponse.class)
            );

        step("Verify response, status code 400", () -> {
            assertEquals("Missing password", response.getError());
        });
    }

    @Test
    void unsuccessfulRegisterRequestMissingUsername() {
        RegisterRequest registerData = new RegisterRequest();
        registerData.setPassword("pistol");

        UnsuccessfulRegisterResponse response =
            step("Sending request without username", () ->
                given()
                    .filter(withCustomTemplates())
                    .log().uri()
                    .log().headers()
                    .log().body()
                    .contentType(ContentType.JSON)
                    .body(registerData)
                .when()
                    .post(requestUri)
                .then()
                    .log().status()
                    .log().body()
                    .statusCode(400)
                    .extract().as(UnsuccessfulRegisterResponse.class)
            );

        step("Verify response, status code 400", () -> {
            assertEquals("Missing email or username", response.getError());
        });
    }

    @Test
    void unsuccessfulRegisterRequestMissingData(){
        RegisterRequest registerData = new RegisterRequest();

        UnsuccessfulRegisterResponse response =
            step("Sending an empty request", () ->
                given()
                    .filter(withCustomTemplates())
                    .log().uri()
                    .log().headers()
                    .log().body()
                    .body(registerData)
                    .contentType(ContentType.JSON)
                .when()
                    .post(requestUri)
                .then()
                    .log().status()
                    .log().body()
                    .statusCode(400)
                    .extract().as(UnsuccessfulRegisterResponse.class)
            );

        step("Verify response, status code 400", () -> {
            assertEquals("Missing email or username", response.getError());
        });
    }

}
