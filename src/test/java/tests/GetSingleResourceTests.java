package tests;

import io.qameta.allure.*;
import models.resources.GetResourcesList;
import models.resources.GetResourcesResponse;

import models.resources.PostResourcesData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;
import static specs.RegisterSpec.*;


public class GetSingleResourceTests {

    String requestUri = "https://reqres.in/api/unknown/";

    @Test
    @Tag("Resource")
    @Feature("Автоматизация тестирования REST API")
    @Story("GET запрос")
    @Owner("Toss Antilles")
    @Link("https://reqres.in/api/unknown/")
    @DisplayName("Успешный запрос.")
    void successfulUnknownRequest2Test(){
        GetResourcesResponse response =
            step("Sending valid request", () ->
                given()
                    .spec(resourcesRequestSpec)
                .when()
                    .get(requestUri+"2")
                .then()
                    .spec(response200Spec)
                    .extract().as(GetResourcesResponse.class)
            );

        step("Verify response: Data", () -> {
            assertEquals("2", response.getData().getId());
            assertEquals("fuchsia rose", response.getData().getName());
            assertEquals("2001", response.getData().getYear());
            assertEquals("#C74375", response.getData().getColor());
            assertEquals("17-2031", response.getData().getPantone_value());
        });
        step("Verify response: Support", () -> {
            assertEquals("https://contentcaddy.io?utm_source=reqres&utm_medium=json&utm_campaign=referral", response.getSupport().getUrl());
            assertEquals("Tired of writing endless social media content? Let Content Caddy generate it for you.",  response.getSupport().getText());
        });
    }



    @Test
    @Tag("Resource")
    @Feature("Автоматизация тестирования REST API")
    @Story("GET запрос")
    @Owner("Toss Antilles")
    @Link("https://reqres.in/api/unknown/")
    @DisplayName("Успешный запрос.")
    void successfulUnknownRequest3Test(){
        GetResourcesResponse response =
            step("Sending valid request", () ->
                given()
                    .spec(resourcesRequestSpec)
                .when()
                    .get(requestUri+3)
                .then()
                    .spec(response200Spec)
                    .extract().as(GetResourcesResponse.class)
            );

        step("Verify response: Data", () -> {
            assertEquals("3", response.getData().getId());
            assertEquals("true red", response.getData().getName());
            assertEquals("2002", response.getData().getYear());
            assertEquals("#BF1932", response.getData().getColor());
            assertEquals("19-1664", response.getData().getPantone_value());
        });
        step("Verify response: Support", () -> {
            assertEquals("https://contentcaddy.io?utm_source=reqres&utm_medium=json&utm_campaign=referral", response.getSupport().getUrl());
            assertEquals("Tired of writing endless social media content? Let Content Caddy generate it for you.",  response.getSupport().getText());
        });
    }

    @Test
    @Tag("Resource")
    @Feature("Автоматизация тестирования REST API")
    @Story("GET запрос")
    @Owner("Toss Antilles")
    @Link("https://reqres.in/api/unknown/")
    @DisplayName("Запрос без параметра id.")
    void successfulUnknownRequestNoIdTest(){
        GetResourcesList response =
            step("Sending valid request with no id", () ->
                given()
                    .spec(resourcesRequestSpec)
                .when()
                    .get(requestUri)
                .then()
                    .spec(response200Spec)
                    .extract().as(GetResourcesList.class)
            );

        step("Verify response: List of data elements: Base", () -> {
            assertEquals("1", response.getPage());
            assertEquals("6", response.getPer_page());
            assertEquals("12", response.getTotal());
            assertEquals("2", response.getTotal_pages());
        });
        step("Verify response: List of data elements: Support", () -> {
            assertEquals("https://contentcaddy.io?utm_source=reqres&utm_medium=json&utm_campaign=referral", response.getSupport().getUrl());
            assertEquals("Tired of writing endless social media content? Let Content Caddy generate it for you.",  response.getSupport().getText());
        });
        step("Verify response: List of data elements: Data element 0", () -> {
            assertEquals("1", response.getData().get(0).getId());
            assertEquals("cerulean", response.getData().get(0).getName());
            assertEquals("2000", response.getData().get(0).getYear());
            assertEquals("#98B2D1", response.getData().get(0).getColor());
            assertEquals("15-4020", response.getData().get(0).getPantone_value());
        });
        step("Verify response: List of data elements: Data element 1", () -> {
            assertEquals("2", response.getData().get(1).getId());
            assertEquals("fuchsia rose", response.getData().get(1).getName());
            assertEquals("2001", response.getData().get(1).getYear());
            assertEquals("#C74375", response.getData().get(1).getColor());
            assertEquals("17-2031", response.getData().get(1).getPantone_value());
        });
    }

    @Test
    @Tag("Resource")
    @Feature("Автоматизация тестирования REST API")
    @Story("GET запрос")
    @Owner("Toss Antilles")
    @Link("https://reqres.in/api/unknown/")
    @DisplayName("Запрос с некорректным id.")
    void successfulUnknownUnexpectedElementTest(){
        GetResourcesResponse response =
            step("Sending request with incorrect id", () ->
                given()
                    .spec(resourcesRequestSpec)
                .when()
                    .get(requestUri+0)
                .then()
                        .spec(response404Spec)
                    .extract().as(GetResourcesResponse.class)
            );

        step("Verify response id empty", () -> {
            assertNull(response.getData());
            assertNull(response.getSupport());
        });

    }

    @Test
    @Tag("Resource")
    @Feature("Автоматизация тестирования REST API")
    @Story("GET запрос")
    @Owner("Toss Antilles")
    @Link("https://reqres.in/api/unknown/")
    @DisplayName("POST звпрос вместо GET")
    void unsuccessfulUnknownRequestPostInsteadOfGetTest (){
        requestUri.concat("1");
        PostResourcesData response =
            step("Sending POST request instead of GET", () ->
                given()
                    .spec(registerRequestSpec)
                .when()
                    .post(requestUri)
                .then()
                    .spec(response201Spec)
                    .extract().as(PostResourcesData.class)
            );

        step("Verify respond is not equal to expected from GET", () -> {
            assertNotEquals(response.getClass(), GetResourcesResponse.class);
        });
    };



}
