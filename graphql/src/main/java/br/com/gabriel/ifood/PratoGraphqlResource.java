package br.com.gabriel.ifood;

import org.eclipse.microprofile.graphql.*;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

@GraphQLApi
public class PratoGraphqlResource {

    @Query("buscarPratos")
    @Description("esta query retorna todos os pratos")
    public List<Prato> buscarTodos() {
        Prato prato = new Prato();
        prato.nome = "Arroz com ovo";
        prato.descricao = "Bife do oião";
        prato.valor = new BigDecimal(BigInteger.TEN);

        return Arrays.asList(prato);
    }

    @Name("restaurante")
    public Restaurante buscarRestaurante(@Source Prato prato) {
        Restaurante restaurante = new Restaurante();
        restaurante.setDono("Gabriel");
        restaurante.setNome("Manquai");
        return restaurante;
    }

    @Mutation
    @Description("Altera o restaurante")
    public Restaurante alterar(Restaurante restaurante) {
        System.out.println(restaurante.getNome());
        return restaurante;
    }
}
