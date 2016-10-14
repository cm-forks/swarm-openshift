package io.swarm.demo;

@Path("/say")
public class HelloClient {

    private final static String USER = "user";
    private final static String PWD = "password";

    @GET
    @Produces("text/plain")
    @Path("hello")
    public Response callHello() {
        System.out.println(">>> Calling the REST Service");
        Client client = ClientBuilder.newClient();
        Response response = client.target("http://localhost:8080/demo/say/echo")
                .queryParam("value","hello")
                .request()
                .accept(MediaType.TEXT_PLAIN_TYPE)
                .get();
        String message = response.readEntity(String.class);
        response.close();
        return Response.ok(message).build();
    }
}