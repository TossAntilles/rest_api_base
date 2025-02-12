package tests;

import io.qameta.allure.Feature;
import io.qameta.allure.Link;
import io.qameta.allure.Owner;
import io.qameta.allure.Story;
import models.register.RegisterRequest;
import models.register.RegisterRequestMoreFields;
import models.register.SuccessfulRegisterResponse;
import models.register.UnsuccessfulRegisterResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static specs.RestApiSpec.*;

public class PostSuccessfulRegisterTests {

    String requestUri = "https://reqres.in/api/register";

    @Test
    @Tag("Register")
    @Feature("Автоматизация тестирования REST API")
    @Story("POST запрос")
    @Owner("Toss Antilles")
    @Link("https://reqres.in/api/register")
    @DisplayName("Запрос с корректным email и password.")
    void successfulRegisterRequestTest(){
        RegisterRequest registerData = new RegisterRequest();
            registerData.setEmail("eve.holt@reqres.in");
            registerData.setPassword("pistol");

        SuccessfulRegisterResponse response =
            step("Sending request with correct email and password", () ->
                given()
                    .spec(registerRequestSpec)
                    .body(registerData)
                .when()
                    .post(requestUri)
                .then()
                    .spec(response200Spec)
                    .extract().as(SuccessfulRegisterResponse.class)
        );

        step("Verify correct response", () -> {
            assertThat(response.getId()).isEqualTo("4");
            assertThat(response.getToken()).hasSize(17);
            assertThat(response.getToken()).containsPattern("^[A-Za-z0-9]*$");
        });

    }

    @Test
    @Tag("Register")
    @Feature("Автоматизация тестирования REST API")
    @Story("POST запрос")
    @Owner("Toss Antilles")
    @Link("https://reqres.in/api/register")
    @DisplayName("Запрос с корректным email и password  избыточными данными.")
    void successfulRegisterRequestAdditionalDataTest(){
        RegisterRequestMoreFields registerData = new RegisterRequestMoreFields();
        registerData.setEmail("janet.weaver@reqres.in");
        registerData.setPassword("pistol");
        registerData.setUserpic("Major.jpg");
        registerData.setSignature("Bzzzzz");

        SuccessfulRegisterResponse response =
            step("Sending request with correct email and password and other fields", () ->
                given()
                    .spec(registerRequestSpec)
                    .body(registerData)
                .when()
                    .post(requestUri)
                .then()
                    .spec(response200Spec)
                    .statusCode(200)
                    .extract().as(SuccessfulRegisterResponse.class)
            );

        step("Verify correct response", () -> {
            assertThat(response.getId()).isEqualTo("2");
            assertThat(response.getToken()).hasSize(17);
            assertThat(response.getToken()).containsPattern("^[A-Za-z0-9]*$");
        });
    }

    @Test
    @Tag("Register")
    @Feature("Автоматизация тестирования REST API")
    @Story("POST запрос")
    @Owner("Toss Antilles")
    @Link("https://reqres.in/api/register")
    @DisplayName("Запрос с некорректным email.")
    void unsuccessfulRegisterUnknownUserTest(){
        RegisterRequest registerData = new RegisterRequest();
        registerData.setEmail("tosst@reqres.in");
        registerData.setPassword("pistol");

        UnsuccessfulRegisterResponse response =
            step("Sending request with unexpected email", () ->
                given()
                    .spec(registerRequestSpec)
                    .body(registerData)
                .when()
                    .post(requestUri)
                .then()
                    .spec(response400Spec)
                    .extract().as(UnsuccessfulRegisterResponse.class)
            );

        step("Verify response, status code 400", () -> {
            assertThat(response.getError()).isEqualTo("Note: Only defined users succeed registration");
        });
    }

    @Test
    @Tag("Register")
    @Feature("Автоматизация тестирования REST API")
    @Story("POST запрос")
    @Owner("Toss Antilles")
    @Link("https://reqres.in/api/register")
    @DisplayName("Запрос с корректным email и пустым password.")
    void unsuccessfulRegisterRequestNullPasswordTest(){
        RegisterRequest registerData = new RegisterRequest();
        registerData.setEmail("eve.holt@reqres.in");
        registerData.setPassword("");

        UnsuccessfulRegisterResponse response =
            step("Sending request with empty password", () ->
                given()
                    .spec(registerRequestSpec)
                    .body(registerData)
                .when()
                    .post(requestUri)
                .then()
                    .spec(response400Spec)
                    .extract().as(UnsuccessfulRegisterResponse.class)
            );

        step("Verify response, status code 400", () -> {
            assertThat(response.getError()).isEqualTo("Missing password");
        });
    }

    @Test
    @Tag("Register")
    @Feature("Автоматизация тестирования REST API")
    @Story("POST запрос")
    @Owner("Toss Antilles")
    @Link("https://reqres.in/api/register")
    @DisplayName("Запрос с корректным email и отсутствующим password.")
    void unsuccessfulRegisterRequestMissingPasswordTest(){
        RegisterRequest registerData = new RegisterRequest();
        registerData.setEmail("eve.holt@reqres.in");

        UnsuccessfulRegisterResponse response =
            step("Sending request without password", () ->
                given()
                    .spec(registerRequestSpec)
                    .body(registerData)
                .when()
                    .post(requestUri)
                .then()
                    .spec(response400Spec)
                    .extract().as(UnsuccessfulRegisterResponse.class)
            );

        step("Verify response, status code 400", () -> {
            assertThat(response.getError()).isEqualTo("Missing password");
        });
    }

    @Test
    @Tag("Register")
    @Feature("Автоматизация тестирования REST API")
    @Story("POST запрос")
    @Owner("Toss Antilles")
    @Link("https://reqres.in/api/register")
    @DisplayName("Запрос без email.")
    void unsuccessfulRegisterRequestMissingUsernameTest() {
        RegisterRequest registerData = new RegisterRequest();
        registerData.setPassword("pistol");

        UnsuccessfulRegisterResponse response =
            step("Sending request without username", () ->
                given()
                    .spec(registerRequestSpec)
                    .body(registerData)
                .when()
                    .post(requestUri)
                .then()
                    .spec(response400Spec)
                    .extract().as(UnsuccessfulRegisterResponse.class)
            );

        step("Verify response, status code 400", () -> {
            assertThat(response.getError()).isEqualTo("Missing email or username");
        });
    }

    @Test
    @Tag("Register")
    @Feature("Автоматизация тестирования REST API")
    @Story("POST запрос")
    @Owner("Toss Antilles")
    @Link("https://reqres.in/api/register")
    @DisplayName("Пустой запрос без email и password.")
    void unsuccessfulRegisterRequestMissingDataTest(){
        RegisterRequest registerData = new RegisterRequest();

        UnsuccessfulRegisterResponse response =
            step("Sending an empty request", () ->
                given()
                    .spec(registerRequestSpec)
                    .body(registerData)
                .when()
                    .post(requestUri)
                .then()
                    .spec(response400Spec)
                    .extract().as(UnsuccessfulRegisterResponse.class)
            );

        step("Verify response, status code 400", () -> {
            assertThat(response.getError()).isEqualTo("Missing email or username");
        });
    }

}
