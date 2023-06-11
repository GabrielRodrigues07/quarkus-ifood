package br.com.gabriel.ifood.resource;

import br.com.gabriel.ifood.dto.AtualizarPratoDTO;
import br.com.gabriel.ifood.dto.CadastrarPratoDTO;
import br.com.gabriel.ifood.dto.PratoDTO;
import br.com.gabriel.ifood.dto.PratoMapper;
import br.com.gabriel.ifood.model.Prato;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Path("pratos")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PratoResource {

    @Inject
    PratoMapper pratoMapper;

    @GET
    public List<PratoDTO> buscar() {
        List<PratoDTO> pratoDTOList = Prato.listAll()
                .stream()
                .map(prato -> pratoMapper.toDTO((Prato) prato))
                .collect(Collectors.toList());
        return pratoDTOList;
    }

    @POST
    @Transactional
    public Response inserir(CadastrarPratoDTO dto) {
        Prato prato = pratoMapper.toPrato(dto);
        prato.persist();
        return Response.status(Response.Status.CREATED).build();
    }

    @PUT
    @Transactional
    @Path("/{id}")
    public void atualizar(@PathParam("id") Long id, AtualizarPratoDTO dto) {
        Optional<Prato> pratoOp = Prato.findByIdOptional(id);
        if (pratoOp.isEmpty()) {
            throw new NotFoundException();
        }
        pratoMapper.toPrato(dto, pratoOp.get());
    }

    @DELETE
    @Transactional
    @Path("/{id}")
    public void deletar(@PathParam("id") Long id) {
        Prato prato = (Prato) Prato.findByIdOptional(id).orElseThrow(NotFoundException::new);
        prato.delete();
    }
}
