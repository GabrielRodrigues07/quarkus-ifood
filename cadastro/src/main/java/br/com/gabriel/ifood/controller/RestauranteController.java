package br.com.gabriel.ifood.controller;

import br.com.gabriel.ifood.dto.AtualizarRestauranteDTO;
import br.com.gabriel.ifood.dto.CadastrarRestauranteDTO;
import br.com.gabriel.ifood.dto.RestauranteDTO;
import br.com.gabriel.ifood.dto.RestauranteMapper;
import br.com.gabriel.ifood.model.Restaurante;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.SimplyTimed;
import org.eclipse.microprofile.metrics.annotation.Timed;
import org.eclipse.microprofile.openapi.annotations.enums.SecuritySchemeType;
import org.eclipse.microprofile.openapi.annotations.security.OAuthFlow;
import org.eclipse.microprofile.openapi.annotations.security.OAuthFlows;
import org.eclipse.microprofile.openapi.annotations.security.SecurityScheme;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
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
@RolesAllowed("proprietario")
@SecurityScheme(securitySchemeName = "ifood-oauth", type = SecuritySchemeType.OAUTH2, flows = @OAuthFlows(password =
@OAuthFlow(tokenUrl = "http://localhost:8180/realms/ifood/protocol/openid-connect/token")))
//@SecurityRequirement(name = "ifood-oauth")
public class RestauranteController {

    @Inject
    RestauranteMapper restauranteMapper;

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
        restaurante.persist();
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
        restauranteMapper.toRestaurante(dto, restaurante);
    }

    @DELETE
    @Path("{id}")
    @Transactional
    public void deletar(@PathParam("id") Long id) {
        Optional<Restaurante> restauranteOp = Restaurante.findByIdOptional(id);
        restauranteOp.ifPresentOrElse(Restaurante::delete, () -> {
            throw new NotFoundException();
        });
    }
}
