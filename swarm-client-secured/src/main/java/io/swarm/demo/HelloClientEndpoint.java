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

/*    public static String getToken() {
        AccessTokenResponse token = null;
        Keycloak keycloak = null;
        try {
            keycloak = KeycloakBuilder.builder()
                    .serverUrl(KEYCLOAK_URL)
                    .realm(REALM)
                    .clientId(CLIENTID)
                    .username(USER)
                    .password(PWD)
                    .build();
            token = keycloak.tokenManager().getAccessToken();
        } catch (NotAuthorizedException nae) {
            System.out.println("You are not authorized to invoke the service");
        }

        if (token != null) {
            System.out.println("Id : " + token.getIdToken());
            System.out.println("Session State : " + token.getSessionState());
            System.out.println("Expires in : " + token.getExpiresIn());
            System.out.println("Token : " + token.getToken());
        }

        return AUTH_HEADER_PREFIX + token;
    }*/

}