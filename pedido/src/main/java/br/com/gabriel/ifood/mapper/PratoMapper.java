package br.com.gabriel.ifood.mapper;


import br.com.gabriel.ifood.model.Prato;
import br.com.gabriel.ifood.model.domain.PratoPedidoDTO;
import org.bson.types.Decimal128;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.math.BigDecimal;

@Mapper(componentModel = "cdi")
public interface PratoMapper {

    @Mapping(target = "nome", source = "nome")
    @Mapping(target = "descricao", source = "descricao")
    @Mapping(target = "preco", source = "preco", qualifiedByName = "mapToDecimal128")
    Prato toMap(PratoPedidoDTO dto);

    @Named("mapToDecimal128")
    default Decimal128 mapToDecimal128(BigDecimal value) {
        return new Decimal128(value);
    }
}
