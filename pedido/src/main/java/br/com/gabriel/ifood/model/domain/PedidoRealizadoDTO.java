package br.com.gabriel.ifood.model.domain;

import lombok.Data;

import java.util.List;

@Data
public class PedidoRealizadoDTO {

    public List<PratoPedidoDTO> pratos;

    public RestauranteDTO restaurante;

    public String cliente;
}
