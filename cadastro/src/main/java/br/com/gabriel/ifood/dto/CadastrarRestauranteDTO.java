package br.com.gabriel.ifood.dto;

import org.hibernate.validator.constraints.br.CNPJ;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class CadastrarRestauranteDTO {

    @Size(min = 3, max = 30)
    public String nomeFantasia;

    @CNPJ
    @NotBlank
    public String cnpj;
    public LocalizacaoDTO localizacao;

}
