package org.acme;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.MediaType;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.containsInAnyOrder;

@QuarkusTest
class ADResourceTest {

    @Test
    void testAList() {
        given()
                .when().get("/authenticatiediensten")
                .then()
                .statusCode(200)
                .body("$.size()", is(2),
                        "name", containsInAnyOrder("AD1", "AD2"),
                        "description", containsInAnyOrder("Eerste Authenticatiedienst", "Tweede Authenticatiedienst")
                );
    }

    @Test
    void testBAdd() {
        given()
                .body("{\n" +
                        "  \"name\": \"Derde\",\n" +
                        "  \"description\": \"Derde Authenticatiedienst\",\n" +
                        "  \"clients\": [\n" +
                        "    {\n" +
                        "      \"some\": \"provider\"\n" +
                        "    }\n" +
                        "  ]\n" +
                        "}")
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .when()
                .post("/authenticatiediensten")
                .then()
                .statusCode(200)
                .body("$.size()", is(3),
                        "name", containsInAnyOrder("AD1", "AD2", "Derde"),
                        "description", containsInAnyOrder("Eerste Authenticatiedienst", "Tweede Authenticatiedienst", "Derde Authenticatiedienst"));

        given()
                .body("{\"name\": \"AD2\", \"description\": \"Tweede Authenticatiedienst\"}")
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .when()
                .delete("/authenticatiediensten")
                .then()
                .statusCode(200)
                .body("$.size()", is(2),
                        "name", containsInAnyOrder("AD1", "Derde"),
                        "description", containsInAnyOrder("Eerste Authenticatiedienst", "Derde Authenticatiedienst"));
    }
}
