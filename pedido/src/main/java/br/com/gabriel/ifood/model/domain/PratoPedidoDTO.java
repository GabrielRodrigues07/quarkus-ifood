package br.com.gabriel.ifood.model.domain;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PratoPedidoDTO {

    public String nome;

    public String descricao;

    public BigDecimal preco;

    public PratoPedidoDTO() {
        super();
    }

    public PratoPedidoDTO(String nome, String descricao, BigDecimal preco) {
        super();
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
    }
}
