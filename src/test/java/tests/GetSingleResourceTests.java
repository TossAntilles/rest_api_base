package tests;

import io.restassured.http.ContentType;
import models.resources.GetResourcesList;
import models.resources.GetResourcesResponse;

import models.resources.PostResourcesData;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;


public class GetSingleResourceTests {

    String requestUri = "https://reqres.in/api/unknown/";


    @Test
    void successfulUnknownRequest2(){
        GetResourcesResponse response =
            step("Sending request", () ->
                given()
                    .log().uri()
                .when()
                    .get(requestUri+"2")
                .then()
                    .log().status()
                    .log().body()
                    .statusCode(200)
                    .extract().as(GetResourcesResponse.class)
            );

        step("Verify response", () -> {
            assertEquals("2", response.getData().getId());
            assertEquals("fuchsia rose", response.getData().getName());
            assertEquals("2001", response.getData().getYear());
            assertEquals("#C74375", response.getData().getColor());
            assertEquals("17-2031", response.getData().getPantone_value());
            assertEquals("https://contentcaddy.io?utm_source=reqres&utm_medium=json&utm_campaign=referral", response.getSupport().getUrl());
            assertEquals("Tired of writing endless social media content? Let Content Caddy generate it for you.",  response.getSupport().getText());
        });
    }



    @Test
    void successfulUnknownRequest3(){
        GetResourcesResponse response =
            step("Sending request", () ->
                given()
                    .log().uri()
                .when()
                    .get(requestUri+3)
                .then()
                    .log().status()
                    .log().body()
                    .statusCode(200)
                    .extract().as(GetResourcesResponse.class)
            );

        step("Verify response", () -> {
            assertEquals("3", response.getData().getId());
            assertEquals("true red", response.getData().getName());
            assertEquals("2002", response.getData().getYear());
            assertEquals("#BF1932", response.getData().getColor());
            assertEquals("19-1664", response.getData().getPantone_value());
            assertEquals("https://contentcaddy.io?utm_source=reqres&utm_medium=json&utm_campaign=referral", response.getSupport().getUrl());
            assertEquals("Tired of writing endless social media content? Let Content Caddy generate it for you.",  response.getSupport().getText());
        });
    }

    @Test
    void successfulUnknownRequestNoId(){
        GetResourcesList response =
            step("Sending request", () ->
                given()
                    .log().uri()
                .when()
                    .get(requestUri)
                .then()
                    .log().status()
                    .log().body()
                    .statusCode(200)
                    .extract().as(GetResourcesList.class)
            );

        step("Verify response", () -> {
            assertEquals("1", response.getPage());
            assertEquals("6", response.getPer_page());
            assertEquals("12", response.getTotal());
            assertEquals("2", response.getTotal_pages());
            assertEquals("1", response.getData().get(0).getId());
            assertEquals("cerulean", response.getData().get(0).getName());
            assertEquals("2000", response.getData().get(0).getYear());
            assertEquals("#98B2D1", response.getData().get(0).getColor());
            assertEquals("15-4020", response.getData().get(0).getPantone_value());
            assertEquals("Tired of writing endless social media content? Let Content Caddy generate it for you.",  response.getSupport().getText());
        });
    }

    @Test
    void successfulUnknownUnexpectedElement(){
        GetResourcesResponse response =
            step("Sending request", () ->
                given()
                    .log().uri()
                .when()
                    .get(requestUri+0)
                .then()
                    .log().status()
                    .log().body()
                    .statusCode(404)
                    .extract().as(GetResourcesResponse.class)
            );

        step("Verify response", () -> {
            assertNull(response.getData());
            assertNull(response.getSupport());
        });

    }



    @Test
    void unsuccessfulUnknownRequestPostInsteadOfGet (){
        requestUri.concat("1");
        PostResourcesData response =
            step("Sending request", () ->
                given()
                    .log().uri()
                    .contentType(ContentType.JSON)
                .when()
                    .post(requestUri)
                .then()
                    .log().status()
                    .log().body()
                    .statusCode(201)
                    .extract().as(PostResourcesData.class)
            );

        step("Verify response", () -> {
            assertNotEquals(response.getClass(), GetResourcesResponse.class);
        });
    };



}
