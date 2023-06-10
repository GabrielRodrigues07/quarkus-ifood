package br.com.gabriel.ifood.model;

import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.Row;
import io.vertx.mutiny.sqlclient.Tuple;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PratoCarrinho {


    public Long id;

    public Long prato;

    public String cliente;

    public static Uni<Long> save(PgPool client, String cliente, Long prato) {
        return client.preparedQuery("INSERT INTO prato_cliente (prato, cliente) VALUES ($1, $2) RETURNING (prato)")
                .execute(Tuple.of(prato, cliente))
                .map(pgRowSet -> pgRowSet.iterator().next().getLong("prato").longValue());
    }

    public static Uni<List<PratoCarrinho>> findCarrinho(PgPool client, String cliente) {
        return client.preparedQuery("SELECT * FROM prato_cliente WHERE cliente = $1 ").execute(Tuple.of(cliente))
                .map(pgRowSet -> {
                    List<PratoCarrinho> list = new ArrayList<>(pgRowSet.size());
                    for (Row row : pgRowSet) {
                        list.add(toPratoCarrinho(row));
                    }
                    return list;
                });
    }

    private static PratoCarrinho toPratoCarrinho(Row row) {
        PratoCarrinho pc = new PratoCarrinho();
        pc.cliente = row.getString("cliente");
        pc.prato = row.getLong("prato");
        return pc;
    }

    public static Uni<Boolean> delete(PgPool client, String cliente) {
        return client.preparedQuery("DELETE FROM prato_cliente WHERE cliente = $1").execute(Tuple.of(cliente))
                .map(pgRowSet -> pgRowSet.rowCount() == 1);
    }

}
