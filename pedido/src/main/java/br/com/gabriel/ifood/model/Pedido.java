package br.com.gabriel.ifood.model;

import io.quarkus.mongodb.panache.PanacheMongoEntity;
import io.quarkus.mongodb.panache.common.MongoEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@MongoEntity(collection = "pedidos", database = "pedido")
public class Pedido extends PanacheMongoEntity {

    public String cliente;
    public List<Prato> pratos;
    public Restaurante restaurante;
    public String entregador;
    public Localizacao localizacaoEntregador;
}
