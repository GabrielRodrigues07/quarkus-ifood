package br.com.gabriel.ifood.controller;

import br.com.gabriel.ifood.model.Prato;

import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;

@Path("pratos")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PratoController {

    @GET
    public List<Prato> buscar() {
        return Prato.listAll();
    }

    @POST
    @Transactional
    public Response inserir(Prato prato) {
        prato.persist();
        return Response.status(Response.Status.CREATED).build();
    }

    @PUT
    @Transactional
    @Path("/{id}")
    public void atualizar(@PathParam("id") Long id, Prato dto) {
        Optional<Prato> pratoOp = Prato.findByIdOptional(id);
        if (pratoOp.isEmpty()) {
            throw new NotFoundException();
        }
        pratoOp.get().nome = dto.nome;
    }

    @DELETE
    @Transactional
    @Path("/{id}")
    public void deletar(@PathParam("id") Long id) {
        Prato prato = (Prato) Prato.findByIdOptional(id).orElseThrow(NotFoundException::new);
        prato.delete();
    }
}
