package io.swarm.demo;

import java.util.Base64;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@ApplicationPath("/call")
public class HelloClientEndpoint {

    private final static String USER = "user";
    private final static String PWD = "password";

    @GET
    @Path("hello")
    public Response callHello() {
        System.out.println(">>> Calling the REST Service");
        Client client = ClientBuilder.newClient();
        Response response = client.target("http://localhost:8080/demo/say/echo")
                .queryParam("value","hello")
                .request()
                .header(HttpHeaders.AUTHORIZATION,"Basic " + getUserPwdEncoded(USER,PWD))
                .accept(MediaType.TEXT_PLAIN_TYPE)
                .get();
        String message = response.readEntity(String.class);
        response.close();
        return Response.ok(message).build();
    }

    public static String getUserPwdEncoded(String user, String pwd) {
        String val = user + ":" + pwd;
        return Base64.getEncoder().encodeToString(val.getBytes());
    }
}