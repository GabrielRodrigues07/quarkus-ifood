package br.com.gabriel.ifood.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.Decimal128;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Prato {

    public String nome;
    public String descricao;
    public Decimal128 preco;
}
