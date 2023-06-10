//package br.com.gabriel.ifood.service.impl;
//
//import br.com.gabriel.ifood.model.Prato;
//import br.com.gabriel.ifood.model.PratoCarrinho;
//import br.com.gabriel.ifood.repository.PratoCarrinhoRepository;
//import br.com.gabriel.ifood.repository.PratoRepository;
//import br.com.gabriel.ifood.service.PratoCarrinhoService;
//import br.com.gabriel.ifood.service.PratoService;
//import io.quarkus.hibernate.reactive.panache.common.runtime.ReactiveTransactional;
//import io.smallrye.mutiny.Uni;
//import lombok.extern.jbosslog.JBossLog;
//
//import javax.enterprise.context.ApplicationScoped;
//import javax.inject.Inject;
//
//@ApplicationScoped
//@JBossLog
//public class PratoServiceImpl implements PratoService {
//
//    @Inject
//    PratoRepository repository;
//
//    @Override
//    public Uni<Prato> findById(Long id) {
//        return repository.findById(id);
//    }
//}
