package br.com.gabriel.ifood.controller;

import br.com.gabriel.ifood.dto.PratoDTO;
import br.com.gabriel.ifood.model.Prato;
import io.smallrye.mutiny.Multi;
import io.vertx.mutiny.pgclient.PgPool;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/restaurantes")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class RestauranteController {

    @Inject
    PgPool pgPool;

    @GET
    @Path("/{id}/pratos")
    public Multi<PratoDTO> buscarPrato(@PathParam("id") Long id) {
        return Prato.findAll(id, pgPool);
    }
}
