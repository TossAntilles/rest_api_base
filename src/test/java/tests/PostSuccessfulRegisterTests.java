package tests;


import models.register.RegisterRequest;
import models.register.RegisterRequestMoreFields;
import models.register.SuccessfulRegisterResponse;
import models.register.UnsuccessfulRegisterResponse;
import org.junit.jupiter.api.Test;

import io.restassured.http.ContentType;

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
            step("Sending request", () ->
                given()
                    .log().uri()
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

        step("Verify response", () -> {
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
            step("Sending request", () ->
                given()
                    .log().uri()
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

        step("Verify response", () -> {
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
            step("Sending request", () ->
                given()
                    .log().uri()
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

        step("Verify response", () -> {
            assertEquals("Note: Only defined users succeed registration", response.getError());
        });
    }

    @Test
    void unsuccessfulRegisterRequestNullPassword(){
        RegisterRequest registerData = new RegisterRequest();
        registerData.setEmail("eve.holt@reqres.in");
        registerData.setPassword("");

        UnsuccessfulRegisterResponse response =
            step("Sending request", () ->
                given()
                    .log().uri()
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

        step("Verify response", () -> {
            assertEquals("Missing password", response.getError());
        });
    }

    @Test
    void unsuccessfulRegisterRequestMissingPassword(){
        RegisterRequest registerData = new RegisterRequest();
        registerData.setEmail("eve.holt@reqres.in");

        UnsuccessfulRegisterResponse response =
            step("Sending request", () ->
                given()
                    .log().uri()
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

        step("Verify response", () -> {
            assertEquals("Missing password", response.getError());
        });
    }

    @Test
    void unsuccessfulRegisterRequestMissingUsername() {
        RegisterRequest registerData = new RegisterRequest();
        registerData.setPassword("pistol");

        UnsuccessfulRegisterResponse response =
            step("Sending request", () ->
                given()
                    .log().uri()
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

        step("Verify response", () -> {
            assertEquals("Missing email or username", response.getError());
        });
    }

    @Test
    void unsuccessfulRegisterRequestMissingData(){
        RegisterRequest registerData = new RegisterRequest();

        UnsuccessfulRegisterResponse response =
            step("Sending request", () ->
                given()
                    .log().uri()
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

        step("Verify response", () -> {
            assertEquals("Missing email or username", response.getError());
        });
    }

}
