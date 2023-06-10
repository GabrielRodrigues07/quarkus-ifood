//package br.com.gabriel.ifood.service.impl;
//
//import br.com.gabriel.ifood.model.PratoCarrinho;
//import br.com.gabriel.ifood.repository.PratoCarrinhoRepository;
//import br.com.gabriel.ifood.service.PratoCarrinhoService;
//import io.smallrye.mutiny.Uni;
//import lombok.extern.jbosslog.JBossLog;
//
//import javax.inject.Inject;
//
//@JBossLog
//public class PratoCarrinhoServiceImpl implements PratoCarrinhoService {
//
//    @Inject
//    PratoCarrinhoRepository repository;
//
//    public Uni<PratoCarrinho> save(PratoCarrinho pratoCarrinho) {
//        return Uni.createFrom().item(pratoCarrinho)
//                .call(e -> repository.persist(e))
//                .onFailure()
//                .invoke(es -> log.infov("Não foi possivel salvar a entidade", es));
//    }
//
//    @Override
//    public Uni<PratoCarrinho> findCarrinho(String cliente) {
//        return repository.buscarPratoPorCliente(cliente);
//    }
//}
