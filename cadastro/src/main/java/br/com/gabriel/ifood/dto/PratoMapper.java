package br.com.gabriel.ifood.dto;

import br.com.gabriel.ifood.model.Prato;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "cdi")
public interface PratoMapper {

    Prato toPrato(CadastrarPratoDTO dto);

    PratoDTO toDTO(Prato prato);

    void toPrato(AtualizarPratoDTO dto, @MappingTarget Prato prato);
}
