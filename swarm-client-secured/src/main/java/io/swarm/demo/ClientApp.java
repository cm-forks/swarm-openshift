package io.swarm.demo;

import java.io.IOException;
import java.net.URL;

import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.client.ClientResponseContext;
import javax.ws.rs.client.ClientResponseFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.logging.Logger;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.representations.AccessTokenResponse;

import static org.keycloak.admin.client.resource.BearerAuthFilter.AUTH_HEADER_PREFIX;

public class ClientApp {

    private final static String KEYCLOAK_URL = "http://localhost:8180/auth";
    private final static String REALM = "basic-auth";
    private final static String CLIENTID = "basic-auth-service";
    private final static String USER = "user";
    private final static String PWD = "password";

    public static void main(String[] args) {
        Client client = ClientBuilder.newClient();
        Response response = client.target("http://localhost:8080/demo/say/echo")
                                  .queryParam("value","hello")
                                  .request()
                                  .header(HttpHeaders.AUTHORIZATION,getToken())
                                  .accept(MediaType.TEXT_PLAIN_TYPE)
                                  .get();
        System.out.println("Status " + response.getStatus());
        System.out.println(response.readEntity(String.class));
        response.close();
    }

    public static String getToken() {
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
    }

}
