package br.com.gabriel.ifood.controller;

import br.com.gabriel.ifood.dto.AtualizarRestauranteDTO;
import br.com.gabriel.ifood.dto.CadastrarRestauranteDTO;
import br.com.gabriel.ifood.dto.RestauranteDTO;
import br.com.gabriel.ifood.dto.RestauranteMapper;
import br.com.gabriel.ifood.model.Restaurante;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Path("/restaurantes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RestauranteController {

    @Inject
    RestauranteMapper restauranteMapper;

    @GET
    public List<RestauranteDTO> buscar() {
        List<RestauranteDTO> restauranteDTOList = Restaurante.listAll()
                .stream()
                .map(restaurante -> restauranteMapper.toDTO((Restaurante) restaurante))
                .collect(Collectors.toList());
        return restauranteDTOList;
    }

    @POST
    @Transactional
    public Response criar(CadastrarRestauranteDTO dto) {
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
