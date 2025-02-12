package specs;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static helpers.CustomAllureListener.withCustomTemplates;
import static io.restassured.RestAssured.with;
import static io.restassured.filter.log.LogDetail.*;

public class RestApiSpec {

    public static RequestSpecification registerRequestSpec = with()
        .filter(withCustomTemplates())
        .log().all()
        .contentType(ContentType.JSON);

    public static RequestSpecification resourcesRequestSpec = with()
        .filter(withCustomTemplates())
        .log().all();


    public static ResponseSpecification response200Spec = new ResponseSpecBuilder()
        .expectStatusCode(200)
        .log(ALL)
        .build();

    public static ResponseSpecification response201Spec = new ResponseSpecBuilder()
            .expectStatusCode(201)
            .log(ALL)
            .build();

    public static ResponseSpecification response400Spec = new ResponseSpecBuilder()
            .expectStatusCode(400)
            .log(ALL)
            .build();

    public static ResponseSpecification response404Spec = new ResponseSpecBuilder()
            .expectStatusCode(404)
            .log(ALL)
            .build();
}
