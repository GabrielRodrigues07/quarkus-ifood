package br.com.gabriel.ifood.resource;

import org.jboss.logging.Logger;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.time.LocalDate;

@Path("/hello")
public class ExampleResource {

    private static final Logger logger = Logger.getLogger(ExampleResource.class);
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        logger.info("Olá Quarkus");
        logger.infof("Olá Quarkus -> %s", LocalDate.now());
        return "Hello from RESTEasy Reactive";
    }
}