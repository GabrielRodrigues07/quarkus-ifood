package br.com.gabriel.ifood.resource;

import br.com.gabriel.ifood.dto.AtualizarRestauranteDTO;
import br.com.gabriel.ifood.dto.CadastrarRestauranteDTO;
import br.com.gabriel.ifood.dto.RestauranteDTO;
import br.com.gabriel.ifood.dto.RestauranteMapper;
import br.com.gabriel.ifood.model.Restaurante;
import io.quarkus.security.Authenticated;
import org.eclipse.microprofile.jwt.Claim;
import org.eclipse.microprofile.jwt.Claims;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.SimplyTimed;
import org.eclipse.microprofile.metrics.annotation.Timed;
import org.eclipse.microprofile.openapi.annotations.enums.SecuritySchemeType;
import org.eclipse.microprofile.openapi.annotations.security.OAuthFlow;
import org.eclipse.microprofile.openapi.annotations.security.OAuthFlows;
import org.eclipse.microprofile.openapi.annotations.security.SecurityScheme;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

import javax.inject.Inject;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Path("/restaurantes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@SecurityScheme(securitySchemeName = "ifood-oauth", type = SecuritySchemeType.OAUTH2, flows = @OAuthFlows(password =
@OAuthFlow(tokenUrl = "http://localhost:8180/realms/ifood/protocol/openid-connect/token")))
//@SecurityRequirement(name = "ifood-oauth")
@Authenticated
public class RestauranteResource {

    @Inject
    RestauranteMapper restauranteMapper;

    @Inject
    @Claim(standard = Claims.sub)
    String sub;

    @Inject
    @Channel("restaurantes")
    Emitter<String> emitter;

    @GET
    @SimplyTimed(name = "Tempo simples de buscas")
    @Timed(name = "tempo completo de busca")
    @Counted(name = "Quantidade de buscas restaurante")
    public List<RestauranteDTO> buscar() {
        List<RestauranteDTO> restauranteDTOList = Restaurante.listAll()
                .stream()
                .map(restaurante -> restauranteMapper.toDTO((Restaurante) restaurante))
                .collect(Collectors.toList());
        return restauranteDTOList;
    }

    @POST
    @Transactional
    public Response criar(@Valid CadastrarRestauranteDTO dto) {
        Restaurante restaurante = restauranteMapper.toRestaurante(dto);
        restaurante.proprietario = sub;
        restaurante.persist();

        // envio de objeto para a fila
        Jsonb create = JsonbBuilder.create();
        String json = create.toJson(restaurante);
        emitter.send(json);

        return Response.status(Response.Status.CREATED).build();
    }

    @PUT
    @Path("{id}")
    @Transactional
    public void atualizar(@PathParam("id") Long id, AtualizarRestauranteDTO dto) {
        Optional<Restaurante> restauranteOp = Restaurante.findByIdOptional(id);
        if (restauranteOp.isEmpty()) {
            throw new NotFoundException();
        }
        Restaurante restaurante = restauranteOp.get();

        if (!restaurante.proprietario.equals(sub)) {
            throw new ForbiddenException("Verifique suas credencias");
        }
        restauranteMapper.toRestaurante(dto, restaurante);
    }

    @DELETE
    @Path("{id}")
    @Transactional
    public void deletar(@PathParam("id") Long id) {
        Optional<Restaurante> restauranteOp = Restaurante.findByIdOptional(id);
        if (!restauranteOp.get().proprietario.equals(sub)) {
            throw new ForbiddenException("Verifique suas credencias");
        }
        restauranteOp.ifPresentOrElse(Restaurante::delete, () -> {
            throw new NotFoundException();
        });
    }
}
