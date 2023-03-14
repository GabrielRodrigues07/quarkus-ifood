package br.com.gabriel.ifood.dto;

import br.com.gabriel.ifood.model.Restaurante;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "cdi")
public interface RestauranteMapper {

    @Mapping(target = "nome", source = "nomeFantasia")
    Restaurante toRestaurante(CadastrarRestauranteDTO dto);
}
