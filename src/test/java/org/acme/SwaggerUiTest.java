package org.acme;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
class SwaggerUiTest {

    // Note: Swagger UI is only available when Quarkus is started in dev or test mode
    @Test
    void testSwaggerUi() {
        given()
                .when().get("/q/swagger-ui")
                .then()
                .statusCode(200)
                .body(containsString("/openapi"));
    }
}
