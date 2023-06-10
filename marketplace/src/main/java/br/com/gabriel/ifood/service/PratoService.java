package br.com.gabriel.ifood.service;

import br.com.gabriel.ifood.model.Prato;
import io.smallrye.mutiny.Uni;

public interface PratoService {

    Uni<Prato> findById(Long id);
}
