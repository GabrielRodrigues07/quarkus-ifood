package br.com.gabriel.ifood.kafka;

import br.com.gabriel.ifood.mapper.PedidoMapper;
import br.com.gabriel.ifood.model.Pedido;
import br.com.gabriel.ifood.model.domain.PedidoRealizadoDTO;
import io.quarkus.mongodb.reactive.ReactiveMongoClient;
import org.eclipse.microprofile.reactive.messaging.Incoming;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class PedidoRealizadoIncoming {

    @Inject
    PedidoMapper mapper;

    @Inject
    ReactiveMongoClient mongoClient;

    @Incoming("pedidos")
    public void lerPedidos(PedidoRealizadoDTO dto) {
        System.out.println(dto);

        Pedido pedido = mapper.toMap(dto);

        pedido.persist();

//        mongoClient.getDatabase("pedido")
//                .getCollection("pedidos", Pedido.class)
//                .insertOne(pedido)
//                .subscribeAsCompletionStage();
    }
}
