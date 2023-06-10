package br.com.gabriel.ifood.util;

import br.com.gabriel.ifood.model.domain.PedidoRealizadoDTO;
import io.quarkus.kafka.client.serialization.ObjectMapperDeserializer;

public class PedidoRealizadoDeserializer extends ObjectMapperDeserializer<PedidoRealizadoDTO> {
    public PedidoRealizadoDeserializer() {
        super(PedidoRealizadoDTO.class);
    }
}
