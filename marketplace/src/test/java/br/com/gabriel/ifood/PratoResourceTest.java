package br.com.gabriel.ifood;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class PratoResourceTest {

    @Test
    public void testBuscarEndpoint() {
        String body = given()
                .when().get("/pratos")
                .then()
                .statusCode(200).extract().asString();
//                .body(is("Hello from RESTEasy Reactive"));

        System.out.println(body);
    }

}