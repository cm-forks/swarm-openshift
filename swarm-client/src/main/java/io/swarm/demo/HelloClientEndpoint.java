package io.swarm.demo;

//import io.fabric8.annotations.ServiceName;
//import io.fabric8.annotations.External;
//import io.fabric8.annotations.Protocol;
//import javax.enterprise.context.ApplicationScoped;
//import javax.inject.Inject;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/say")
@ApplicationScoped
public class HelloClientEndpoint {

    private final static String USER = "user";
    private final static String PWD = "password";

    private String helloService = "http://swarm-rest";

    @GET
    @Produces("text/plain")
    @Path("hello")
    public Response callHello() {
        System.out.println(">>> Calling the REST Service");
        System.out.println(">>> Hello Service : " + helloService);
        // String helloService = "http://localhost:8080" + "/say/echo";
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