package br.com.gabriel.ifood.mapper;

import br.com.gabriel.ifood.model.Pedido;
import br.com.gabriel.ifood.model.domain.PedidoRealizadoDTO;
import org.mapstruct.Mapper;

import javax.enterprise.context.ApplicationScoped;

@Mapper(componentModel = "cdi", uses = PratoMapper.class)
@ApplicationScoped
public interface PedidoMapper {

    Pedido toMap(PedidoRealizadoDTO dto);
}
