//package br.com.gabriel.ifood.repository;
//
//import br.com.gabriel.ifood.model.PratoCarrinho;
//import io.quarkus.hibernate.reactive.panache.PanacheRepository;
//import io.smallrye.mutiny.Uni;
//import lombok.extern.jbosslog.JBossLog;
//
//import javax.enterprise.context.ApplicationScoped;
//
//@ApplicationScoped
//@JBossLog
//public class PratoCarrinhoRepository implements PanacheRepository<PratoCarrinho> {
//
//    public Uni<PratoCarrinho> buscarPratoPorCliente(String cliente) {
//        return find("cliente", cliente).firstResult();
//    }
//}
