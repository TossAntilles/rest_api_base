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
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static specs.RestApiSpec.*;


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
            assertThat(response.getData().getId()).isEqualTo("2");
            assertThat(response.getData().getName()).isEqualTo("fuchsia rose");
            assertThat(response.getData().getYear()).isEqualTo("2001");
            assertThat(response.getData().getColor()).isEqualTo("#C74375");
            assertThat(response.getData().getPantone_value()).isEqualTo("17-2031");
        });
        step("Verify response: Support", () -> {
            assertThat(response.getSupport().getUrl()).isEqualTo("https://contentcaddy.io?utm_source=reqres&utm_medium=json&utm_campaign=referral");
            assertThat(response.getSupport().getText()).isEqualTo("Tired of writing endless social media content? Let Content Caddy generate it for you.");
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
            assertThat(response.getData().getId()).isEqualTo("3");
            assertThat(response.getData().getName()).isEqualTo("true red");
            assertThat(response.getData().getYear()).isEqualTo("2002");
            assertThat(response.getData().getColor()).isEqualTo("#BF1932");
            assertThat(response.getData().getPantone_value()).isEqualTo("19-1664");
        });
        step("Verify response: Support", () -> {
            assertThat(response.getSupport().getUrl()).isEqualTo("https://contentcaddy.io?utm_source=reqres&utm_medium=json&utm_campaign=referral");
            assertThat(response.getSupport().getText()).isEqualTo("Tired of writing endless social media content? Let Content Caddy generate it for you.");
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
            assertThat(response.getPage()).isEqualTo("1");
            assertThat(response.getPer_page()).isEqualTo("6");
            assertThat(response.getTotal()).isEqualTo("12");
            assertThat(response.getTotal_pages()).isEqualTo("2");
        });
        step("Verify response: Support", () -> {
            assertThat(response.getSupport().getUrl()).isEqualTo("https://contentcaddy.io?utm_source=reqres&utm_medium=json&utm_campaign=referral");
            assertThat(response.getSupport().getText()).isEqualTo("Tired of writing endless social media content? Let Content Caddy generate it for you.");
        });
        step("Verify response: List of data elements: Data element 0", () -> {
            assertThat(response.getData().get(0).getId()).isEqualTo("1");
            assertThat(response.getData().get(0).getName()).isEqualTo("cerulean");
            assertThat(response.getData().get(0).getYear()).isEqualTo("2000");
            assertThat(response.getData().get(0).getColor()).isEqualTo("#98B2D1");
            assertThat(response.getData().get(0).getPantone_value()).isEqualTo("15-4020");
        });
        step("Verify response: List of data elements: Data element 1", () -> {
            assertThat(response.getData().get(1).getId()).isEqualTo("2");
            assertThat(response.getData().get(1).getName()).isEqualTo("fuchsia rose");
            assertThat(response.getData().get(1).getYear()).isEqualTo("2001");
            assertThat(response.getData().get(1).getColor()).isEqualTo("#C74375");
            assertThat(response.getData().get(1).getPantone_value()).isEqualTo("17-2031");
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
            assertThat(response.getData()).isNull();
            assertThat(response.getSupport()).isNull();
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
            assertThat(response.getClass()).isNotEqualTo(GetResourcesResponse.class);
        });
    };



}
