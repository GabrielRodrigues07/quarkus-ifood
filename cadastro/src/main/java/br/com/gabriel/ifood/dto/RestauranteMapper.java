package br.com.gabriel.ifood.dto;

import br.com.gabriel.ifood.model.Restaurante;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "cdi")
public interface RestauranteMapper {

    @Mapping(target = "nome", source = "nomeFantasia")
    Restaurante toRestaurante(CadastrarRestauranteDTO dto);

    @Mapping(target = "nomeFantasia", source = "nome")
    @Mapping(target = "dataCriacao", dateFormat = "dd/MM/yyyy HH:mm:ss")
    RestauranteDTO toDTO(Restaurante restaurante);

    @Mapping(target = "nome", source = "nomeFantasia")
    void toRestaurante(AtualizarRestauranteDTO dto, @MappingTarget Restaurante restaurante);
}
