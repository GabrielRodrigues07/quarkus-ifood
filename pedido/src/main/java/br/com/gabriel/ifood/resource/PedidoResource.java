package br.com.gabriel.ifood.resource;

import br.com.gabriel.ifood.model.Localizacao;
import br.com.gabriel.ifood.model.Pedido;
import io.quarkus.mongodb.panache.PanacheMongoEntityBase;
import io.vertx.core.Vertx;
import io.vertx.ext.bridge.PermittedOptions;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.sockjs.SockJSBridgeOptions;
import io.vertx.ext.web.handler.sockjs.SockJSHandler;
import io.vertx.mutiny.core.eventbus.EventBus;
import org.bson.types.ObjectId;

import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.json.bind.JsonbBuilder;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/pedidos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PedidoResource {

    @Inject
    Vertx vertx;

    @Inject
    EventBus eventBus;
    void startup(@Observes Router router) {
        router.route("/localizacoes*").handler(localizacaoHandler());
    }

    private SockJSHandler localizacaoHandler() {
        SockJSHandler handler = SockJSHandler.create(vertx);
        PermittedOptions permittedOptions = new PermittedOptions();
        permittedOptions.setAddress("novaLocalizacao");
        SockJSBridgeOptions bridgeOptions = new SockJSBridgeOptions().addOutboundPermitted(permittedOptions);
        handler.bridge(bridgeOptions);
        return handler;
    }

    @GET
    public List<PanacheMongoEntityBase> listAll() {
        return Pedido.listAll();
    }

    @POST
    @Path("/{idPedido}")
    public PanacheMongoEntityBase save(@PathParam("idPedido") String idPedido, Localizacao localizacao) {
        Pedido pedido = (Pedido) Pedido.findByIdOptional(new ObjectId(idPedido)).orElseThrow(() -> new IllegalStateException("Pedido não encontrado"));

        pedido.setLocalizacaoEntregador(localizacao);

        String json = JsonbBuilder.create().toJson(localizacao);

        eventBus.send("novaLocalizacao", json);
        pedido.persistOrUpdate();

        return pedido;
    }
}