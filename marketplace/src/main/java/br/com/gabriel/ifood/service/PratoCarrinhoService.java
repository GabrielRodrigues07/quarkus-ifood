package br.com.gabriel.ifood.service;

import br.com.gabriel.ifood.model.PratoCarrinho;
import io.smallrye.mutiny.Uni;

public interface PratoCarrinhoService {

    Uni<PratoCarrinho> save(PratoCarrinho pratoCarrinho);

    Uni<PratoCarrinho> findCarrinho(String cliente);

}
