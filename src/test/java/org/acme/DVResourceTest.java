package org.acme;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.MediaType;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.containsInAnyOrder;

@QuarkusTest
class DVResourceTest {
    @Test
    void testAList() {
        given()
                .when().get("/dienstverlener")
                .then()
                .statusCode(200)
                .body("$.size()", is(2),
                        "name", containsInAnyOrder("DV1", "DV2"),
                        "description", containsInAnyOrder("Eerste Dienstverlener", "Tweede Dienstverlener")
                );
    }

    @Test
    void testBDVd() {
        given()
                .body("{\n" +
                        "  \"name\": \"Derde\",\n" +
                        "  \"description\": \"Derde Dienstverlener\",\n" +
                        "  \"provider\": [\n" +
                        "    {\n" +
                        "      \"some\": \"provider\"\n" +
                        "    }\n" +
                        "  ]\n" +
                        "}")
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .when()
                .post("/dienstverlener")
                .then()
                .statusCode(200)
                .body("$.size()", is(3),
                        "name", containsInAnyOrder("DV1", "DV2", "Derde"),
                        "description", containsInAnyOrder("Eerste Dienstverlener", "Tweede Dienstverlener", "Derde Dienstverlener"));

        given()
                .body("{\"name\": \"DV2\", \"description\": \"Tweede Dienstverlener\"}")
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .when()
                .delete("/dienstverlener")
                .then()
                .statusCode(200)
                .body("$.size()", is(2),
                        "name", containsInAnyOrder("DV1", "Derde"),
                        "description", containsInAnyOrder("Eerste Dienstverlener", "Derde Dienstverlener"));
    }
}
