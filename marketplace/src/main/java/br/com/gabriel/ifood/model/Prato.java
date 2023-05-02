package br.com.gabriel.ifood.model;

import br.com.gabriel.ifood.dto.PratoDTO;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.Row;
import io.vertx.mutiny.sqlclient.RowSet;
import io.vertx.mutiny.sqlclient.Tuple;

import java.math.BigDecimal;
import java.util.stream.StreamSupport;

public class Prato {


    public Long id;
    public String nome;
    public String descricao;
    public Restaurante restaurante;
    public BigDecimal preco;

    public static Multi<PratoDTO> findAll(PgPool pgPool) {
        Uni<RowSet<Row>> preparedQuery = pgPool.query("SELECT * FROM prato").execute();
        return preparedQuery.onItem().transformToMulti(rowSet -> Multi.createFrom().items(() -> StreamSupport.stream(rowSet.spliterator(), false))).onItem().transform(PratoDTO::from);
    }

    public static Multi<PratoDTO> findAll(Long id, PgPool pgPool) {
        Uni<RowSet<Row>> uni = pgPool.preparedQuery("SELECT * FROM prato WHERE prato.restaurante_id= $1 ORDER BY nome ASC").execute(Tuple.of(id));
        return unitToMulti(uni);
    }

    private static Multi<PratoDTO> unitToMulti(Uni<RowSet<Row>> uni) {
        return uni.onItem().transformToMulti(rowSet -> Multi.createFrom().items(() -> StreamSupport.stream(rowSet.spliterator(), false))).onItem().transform(PratoDTO::from);
    }

    public static Uni<PratoDTO> findById(PgPool client, Long id) {
        return client.preparedQuery("SELECT * FROM prato WHERE id = $1").execute(Tuple.of(id))
                .map(RowSet::iterator)
                .map(iterator -> iterator.hasNext() ? PratoDTO.from(iterator.next()) : null);
    }
}
