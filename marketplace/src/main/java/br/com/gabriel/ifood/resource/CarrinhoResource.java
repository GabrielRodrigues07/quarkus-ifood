package br.com.gabriel.ifood.resource;

import br.com.gabriel.ifood.dto.PedidoRealizadoDTO;
import br.com.gabriel.ifood.dto.PratoDTO;
import br.com.gabriel.ifood.dto.PratoPedidoDTO;
import br.com.gabriel.ifood.dto.RestauranteDTO;
import br.com.gabriel.ifood.model.Prato;
import br.com.gabriel.ifood.model.PratoCarrinho;
import io.smallrye.mutiny.Uni;
import lombok.extern.jbosslog.JBossLog;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.stream.Collectors;

@Path("carrinho")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
@JBossLog
public class CarrinhoResource {

    private static final String CLIENTE = "a";

    @Inject
    io.vertx.mutiny.pgclient.PgPool client;

    @Inject
    @Channel("pedidos")
    Emitter<PedidoRealizadoDTO> messageEmitter;


    @GET
    public Uni<List<PratoCarrinho>> buscarcarrinho() {
        return PratoCarrinho.findCarrinho(client, CLIENTE);
    }

    @POST
    @Path("/prato/{idPrato}")
    public Uni<Long> adicionarPrato(@PathParam("idPrato") Long idPrato) {
        //poderia retornar o pedido atual
        PratoCarrinho pc = new PratoCarrinho();
        pc.cliente = CLIENTE;
        pc.prato = idPrato;
        return PratoCarrinho.save(client, CLIENTE, idPrato);

    }

    @POST
    @Path("/realizar-pedido")
    public Uni<Boolean> finalizar() {
        PedidoRealizadoDTO pedido = new PedidoRealizadoDTO();
        String cliente = CLIENTE;
        pedido.cliente = cliente;
        List<PratoCarrinho> pratoCarrinho = PratoCarrinho.findCarrinho(client, cliente).await().indefinitely();
        //Utilizar mapstruts
        List<PratoPedidoDTO> pratos = pratoCarrinho.stream().map(pc -> from(pc)).collect(Collectors.toList());
        pedido.pratos = pratos;

        RestauranteDTO restaurante = new RestauranteDTO();
        restaurante.nome = "nome restaurante";
        pedido.restaurante = restaurante;
        messageEmitter.send(pedido);
        return PratoCarrinho.delete(client, cliente);
    }

    private PratoPedidoDTO from(PratoCarrinho pratoCarrinho) {
        PratoDTO dto = Prato.findById(client, pratoCarrinho.prato).await().indefinitely();
        return new PratoPedidoDTO(dto.nome, dto.descricao, dto.preco);
    }
}