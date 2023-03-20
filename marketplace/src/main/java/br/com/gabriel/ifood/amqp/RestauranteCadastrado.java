package br.com.gabriel.ifood.amqp;

import br.com.gabriel.ifood.model.Restaurante;
import org.eclipse.microprofile.reactive.messaging.Incoming;

import javax.enterprise.context.ApplicationScoped;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;

@ApplicationScoped
public class RestauranteCadastrado {

    @Incoming("restaurantes")
    public void receberRestaurante(String s) {
        Jsonb create = JsonbBuilder.create();
        Restaurante restaurante = create.fromJson(s, Restaurante.class);

        System.out.println("------------------------");

        System.out.println(s);
        System.out.println("------------------------");

        System.out.println(restaurante);
    }
}
