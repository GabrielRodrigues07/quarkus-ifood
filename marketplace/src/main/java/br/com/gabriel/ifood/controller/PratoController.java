package br.com.gabriel.ifood.controller;

import br.com.gabriel.ifood.dto.PratoDTO;
import br.com.gabriel.ifood.model.Prato;
import io.smallrye.mutiny.Multi;
import io.vertx.mutiny.pgclient.PgPool;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/pratos")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PratoController {

    @Inject
    PgPool pgPool;

    @GET
    public Multi<PratoDTO> buscarPratos() {
        return Prato.findAll(pgPool);
    }
}
