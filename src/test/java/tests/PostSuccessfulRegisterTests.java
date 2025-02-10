package tests;


import models.register.RegisterRequestModel;
import models.register.SuccessfulRegisterResponseModel;
import models.register.UnsuccessfulRegisterResponseModel;
import org.junit.jupiter.api.Test;

import io.restassured.http.ContentType;
import static io.restassured.RestAssured.given;

public class PostSuccessfulRegisterTests {

    String requestUri = "https://reqres.in/api/register";


    @Test
    void successfulRegisterRequest(){
        RegisterRequestModel registerData = new RegisterRequestModel();
            registerData.setEmail("eve.holt@reqres.in");
            registerData.setPassword("pistol");

        SuccessfulRegisterResponseModel response =
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
            .extract().as(SuccessfulRegisterResponseModel.class);
    }

    @Test
    void successfulRegisterRequestExternalData(){
        RegisterRequestModel registerData = new RegisterRequestModel();
        registerData.setEmail("janet.weaver@reqres.in");
        registerData.setPassword("pistol");

        SuccessfulRegisterResponseModel response =
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
                .extract().as(SuccessfulRegisterResponseModel.class);
    }

    @Test
    void unsuccessfulRegisterUnknownUser(){
        RegisterRequestModel registerData = new RegisterRequestModel();
        registerData.setEmail("tosst@reqres.in");
        registerData.setPassword("pistol");

        UnsuccessfulRegisterResponseModel response =
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
                .extract().as(UnsuccessfulRegisterResponseModel.class);
    }

    @Test
    void unsuccessfulRegisterRequestNullPassword(){
        RegisterRequestModel registerData = new RegisterRequestModel();
        registerData.setEmail("eve.holt@reqres.in");
        registerData.setPassword("");

        UnsuccessfulRegisterResponseModel response =
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
                .extract().as(UnsuccessfulRegisterResponseModel.class);
    }

    @Test
    void unsuccessfulRegisterRequestMissingPassword(){
        RegisterRequestModel registerData = new RegisterRequestModel();
        registerData.setEmail("eve.holt@reqres.in");

        UnsuccessfulRegisterResponseModel response =
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
                .extract().as(UnsuccessfulRegisterResponseModel.class);
    }

    @Test
    void unsuccessfulRegisterRequestMissingUsername() {
        RegisterRequestModel registerData = new RegisterRequestModel();
        registerData.setPassword("pistol");

        UnsuccessfulRegisterResponseModel response =
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
                .extract().as(UnsuccessfulRegisterResponseModel.class);
    }

    @Test
    void unsuccessfulRegisterRequestMissingData(){
        RegisterRequestModel registerData = new RegisterRequestModel();

        UnsuccessfulRegisterResponseModel response =
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
                .extract().as(UnsuccessfulRegisterResponseModel.class);
    }

}
