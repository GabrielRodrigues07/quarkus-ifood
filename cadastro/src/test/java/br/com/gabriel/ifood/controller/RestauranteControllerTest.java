package br.com.gabriel.ifood.controller;

import br.com.gabriel.ifood.dto.RestauranteDTO;
import br.com.gabriel.ifood.model.Restaurante;
import br.com.gabriel.ifood.util.TestsUtils;
import com.github.database.rider.cdi.api.DBRider;
import com.github.database.rider.core.api.configuration.DBUnit;
import com.github.database.rider.core.api.configuration.Orthography;
import com.github.database.rider.core.api.dataset.DataSet;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@DBRider
@DBUnit(caseInsensitiveStrategy = Orthography.LOWERCASE)
@QuarkusTest
@QuarkusTestResource(CadastroTestLifecycleManager.class)
public class RestauranteControllerTest {

    private String keycloakUrl;
    private String token;

    @BeforeEach
    public void setUp() {
        // Configurações do cliente Keycloak
        keycloakUrl = "http://localhost:8180/realms/ifood";
        String clientId = "front-web-cadastro";
        String clientSecret = "TlVwwW8O8iCXAqTNI06Dm7OuWPJ0YCMc";
        String username = "proprietario1";
        String password = "senha123";

        // Obtém um token de acesso válido do Keycloak
        token = given()
                .contentType("application/x-www-form-urlencoded")
                .formParam("grant_type", "password")
                .formParam("client_id", clientId)
                .formParam("client_secret", clientSecret)
                .formParam("username", username)
                .formParam("password", password)
                .when()
                .post(keycloakUrl + "/protocol/openid-connect/token")
                .then()
                .statusCode(200)
                .extract()
                .path("access_token");
    }

    private RequestSpecification given() {
        return RestAssured.given().contentType(ContentType.JSON);
    }

    @Test
    @DataSet("restaurantes-cenario-1.yml")
    @DisplayName("deve retornar uma lista de restaurantes")
    public void testeBuscarRestaurante() throws IOException {
        List<RestauranteDTO> restauranteListExpected = TestsUtils.getContracts("src/test/java/br/com/gabriel/ifood/controller/resource/expected.json");

        List<RestauranteDTO> resultado = given()
                .header("Authorization", "Bearer " + token)
                .when().get("/restaurantes")
                .then()
                .statusCode(200)
                .extract()
                .body()
                .as(new TypeRef<>() {});

        Assertions.assertEquals(2, resultado.size());
    }

    @Test
    @DisplayName("deve cadastrar um restaurante")
    public void testeCadastrarRestaurante() {
        Restaurante restaurante = new Restaurante();
        restaurante.nome = "nome";
        restaurante.proprietario = "prop";
        restaurante.cnpj = "81329267000129";

        given().with().body(restaurante)
                .header("Authorization", "Bearer " + token)
                .when().post("/restaurantes")
                .then()
                .statusCode(201);
    }

    @Test
    @DataSet("restaurantes-cenario-1.yml")
    @DisplayName("deve atualizar o nome fantasia de um restaurante")
    public void testeAtualizarRestaurante() {
        RestauranteDTO dto = new RestauranteDTO();
        dto.nomeFantasia = "novo nome";
        Long id = 123L;

        given().with().body(dto).pathParam("id", id)
                .header("Authorization", "Bearer " + token)
                .when().put("/restaurantes/{id}")
                .then()
                .statusCode(204);

        List<RestauranteDTO> resultado = given()
                .header("Authorization", "Bearer " + token)
                .when().get("/restaurantes")
                .then()
                .statusCode(200)
                .extract()
                .body()
                .as(new TypeRef<>() {});

        String nomeFantasia = resultado.get(1).nomeFantasia;

        Assertions.assertEquals(dto.nomeFantasia, nomeFantasia);
    }

    @Test
    @DataSet("restaurantes-cenario-1.yml")
    @DisplayName("deve deletar um restaurante")
    public void testeDeletarRestaurante() {
        Long id = 123L;
        given().with().pathParam("id", id)
                .header("Authorization", "Bearer " + token)
                .when().delete("/restaurantes/{id}")
                .then()
                .statusCode(204);

        Assertions.assertNull(Restaurante.findById(id));
    }
}
