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
        Uni<RowSet<Row>> preparedQuery = pgPool.query("select * from prato").execute();
        return preparedQuery.onItem().transformToMulti(rowSet -> Multi.createFrom().items(() -> StreamSupport.stream(rowSet.spliterator(), false))).onItem().transform(PratoDTO::from);
    }

    public static Multi<PratoDTO> findAll(Long id, PgPool pgPool) {
        Uni<RowSet<Row>> uni = pgPool.preparedQuery("select * from prato where prato.restaurante_id= $1 order by nome ASC").execute(Tuple.of(id));
        return unitToMulti(uni);
    }

    private static Multi<PratoDTO> unitToMulti(Uni<RowSet<Row>> uni) {
        return uni.onItem().transformToMulti(rowSet -> Multi.createFrom().items(() -> StreamSupport.stream(rowSet.spliterator(), false))).onItem().transform(PratoDTO::from);
    }
}
