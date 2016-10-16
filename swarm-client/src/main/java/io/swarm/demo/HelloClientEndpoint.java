package io.swarm.demo;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.wildfly.swarm.spi.runtime.annotations.ConfigurationValue;

@Path("/say")
@ApplicationScoped
public class HelloClientEndpoint {

    private final static String USER = "user";
    private final static String PWD = "password";

    @Inject
    @ConfigurationValue("service.hello.url")
    private String helloService;

    @GET
    @Produces("text/plain")
    @Path("hello")
    public Response callHello() {
        System.out.println(">>> Calling the Hello REST Service");
        System.out.println(">>> Hello Service : " + helloService);
        Client client = ClientBuilder.newClient();
        Response response = client.target(helloService + "/say/hello")
                .queryParam("value","hello")
                .request()
                .accept(MediaType.TEXT_PLAIN_TYPE)
                .get();
        String message = response.readEntity(String.class);
        response.close();
        return Response.ok(message).build();
    }
}