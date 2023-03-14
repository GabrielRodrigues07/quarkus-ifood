package br.com.gabriel.ifood.controller;

import br.com.gabriel.ifood.model.Restaurante;
import com.github.database.rider.cdi.api.DBRider;
import com.github.database.rider.core.api.configuration.DBUnit;
import com.github.database.rider.core.api.configuration.Orthography;
import com.github.database.rider.core.api.dataset.DataSet;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.approvaltests.Approvals;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Optional;

@DBRider
@DBUnit(caseInsensitiveStrategy = Orthography.LOWERCASE)
@QuarkusTest
@QuarkusTestResource(CadastroTestLifecycleManager.class)
public class RestauranteControllerTest {

    private RequestSpecification given() {
        return RestAssured.given().contentType(ContentType.JSON);
    }

    @Test
    @DataSet("restaurantes-cenario-1.yml")
    public void testeBuscarRestaurante() {
        String resultado = RestAssured.given()
                .when().get("/restaurantes")
                .then()
                .statusCode(200)
                .extract().asString();
        Approvals.verifyJson(resultado);
    }

    @Test
    public void testeCadastrarRestaurante() {
        Restaurante restaurante = new Restaurante();
        restaurante.nome = "nome";
        restaurante.proprietario = "prop";

        given().with().body(restaurante)
                .when().post("/restaurantes")
                .then()
                .statusCode(201);
    }

    @Test
    @DataSet("restaurantes-cenario-1.yml")
    public void testeAtualizarRestaurante() {
        Restaurante dto = new Restaurante();
        dto.nome = "novo nome";
        dto.proprietario = "prop";
        Long id = 123L;

        given().with().body(dto).pathParam("id", id)
                .when().put("/restaurantes/{id}")
                .then()
                .statusCode(204);

        Optional<Restaurante> restOp = Restaurante.findByIdOptional(id);
        Assertions.assertEquals(dto.nome, restOp.get().nome);
    }

    @Test
    @DataSet("restaurantes-cenario-1.yml")
    public void testeDeletarRestaurante() {
        Long id = 123L;
        given().with().pathParam("id", id)
                .when().delete("/restaurantes/{id}")
                .then()
                .statusCode(204);

        Assertions.assertNull(Restaurante.findById(id));
    }
}
