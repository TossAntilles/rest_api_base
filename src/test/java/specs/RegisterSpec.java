package specs;

import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.with;

public class RegisterSpec {

    public static RequestSpecification registerSpec = with()
            .log().uri()
            .log().headers()
            .log().body()
            .contentType(ContentType.JSON);


}
