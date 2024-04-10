package org.acme;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
class FraudResourceTest {

    @Test
    void testNoneFraudEndpoint() {
        given()
          .contentType(ContentType.JSON)
                .and()
                .body(TransactionDetailsFactory.getNoneFraudTransaction())
          .when().post("/fraud")
          .then()
             .statusCode(200)
             .body(is("false"));
    }

    @Test
    void testFraudEndpoint() {
        given()
                .contentType(ContentType.JSON)
                .and()
                .body(TransactionDetailsFactory.getFraudTransaction())
                .when().post("/fraud")
                .then()
                .statusCode(200)
                .body(is("true"));
    }

}