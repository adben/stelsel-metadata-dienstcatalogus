package org.acme;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.MediaType;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.containsInAnyOrder;

@QuarkusTest
class RPResourceTest {
    @Test
    void testAList() {
        given()
                .when().get("/relyingparties")
                .then()
                .statusCode(200)
                .body("RPs.size()", is(1),
                        "RPs.name", containsInAnyOrder("client-dienstverlener-DV1"),
                        "RPs.description", containsInAnyOrder("Eerste Dienstverlener")
                );
    }

    @Test
    void testBRPd() {
        given()
                .body("""
                        {
                          "type": "oidc",
                          "name": "Lutjebroek",
                          "description": "Lutjebroek vergunningen",
                          "consentText": "Mooie consent text voor Lutjebroek Vergunningen",
                          "minimumLoa": "high",
                          "clientId": "lb-verg-oidc",
                          "jwks_uri": "https://certs.example.com",
                          "redirect_uris": ["https://app.example.com/*"],
                          "post_logout_redirect_uri": []
                        }""")
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .when()
                .post("/relyingparties")
                .then()
                .statusCode(204);

        given()
                .when()
                .delete("/relyingparties/client-dv1")
                .then()
                .statusCode(204);

        given().when().get("/relyingparties")
                .then()
                .body("RPs.size()", is(1),
                        "RPs.name", containsInAnyOrder("Lutjebroek"),
                        "RPs.description", containsInAnyOrder("Lutjebroek vergunningen"));
    }
}
