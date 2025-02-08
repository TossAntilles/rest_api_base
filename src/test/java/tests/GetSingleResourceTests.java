package tests;

import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.Is.isA;

public class GetSingleResourceTests {

    @Test
    void successfulUnknownRequest2(){
        String requestUri = "https://reqres.in/api/unknown/2";
        given()
            .log().uri()
        .when()
            .get(requestUri)
        .then()
            .log().status()
            .log().body()
            .statusCode(200)
            .body("data.id", is(2))
            .body("data.name", is("fuchsia rose"))
            .body("data.year", is(2001))
            .body("data.color", is("#C74375"))
            .body("data.pantone_value", is("17-2031"))
            .body("support.url", is("https://contentcaddy.io?utm_source=reqres&utm_medium=json&utm_campaign=referral"))
            .body("support.text", is("Tired of writing endless social media content? Let Content Caddy generate it for you."));
    }

    @Test
    void successfulUnknownRequest3(){
        String requestUri = "https://reqres.in/api/unknown/3";
        given()
            .log().uri()
        .when()
            .get(requestUri)
        .then()
            .log().status()
            .log().body()
            .statusCode(200)
            .body("data.id", is(3))
            .body("data.name", is("true red"))
            .body("data.year", is(2002))
            .body("data.color", is("#BF1932"))
            .body("data.pantone_value", is("19-1664"))
            .body("support.url", is("https://contentcaddy.io?utm_source=reqres&utm_medium=json&utm_campaign=referral"))
            .body("support.text", is("Tired of writing endless social media content? Let Content Caddy generate it for you."));
    }

    @Test
    void successfulUnknownRequestNoId(){
        String requestUri = "https://reqres.in/api/unknown/";
        given()
            .log().uri()
        .when()
            .get(requestUri)
        .then()
            .log().status()
            .log().body()
            .statusCode(200)
            .body("data", isA(List.class));
    }

    @Test
    void unsuccessfulUnknownRequestPostInsteadOfGet (){
        String requestUri = "https://reqres.in/api/unknown/3";
        given()
            .log().uri()
        .when()
            .post(requestUri)
        .then()
            .log().status()
            .log().body()
            .statusCode(415);
    }

    @Test
    void successfulUnknownUnexpectedElement(){
        String requestUri = "https://reqres.in/api/unknown/0";
        given()
            .log().uri()
        .when()
            .get(requestUri)
        .then()
            .log().status()
            .log().body()
            .statusCode(404);
    }

}
