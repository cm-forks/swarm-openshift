package io.swarm.demo;

import java.io.IOException;

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

public class ClientPolicy {

    public static void main(String[] args) {
        AccessTokenResponse token = null;
        Keycloak keycloak = null;
        try {
            keycloak = KeycloakBuilder.builder()
                    .serverUrl("http://localhost:8180/auth")
                    .realm("basic-auth")
                    .clientId("basic-auth-service")
                    .username("user")
                    .password("password")
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

        String authHeader = AUTH_HEADER_PREFIX + token;
        Client client = ClientBuilder.newClient();

        Response response = client.target("http://localhost:8080/say/echo")
                                  .queryParam("value","hello")
                                  .request()
                                  .header(HttpHeaders.AUTHORIZATION,authHeader)
                                  .accept(MediaType.TEXT_PLAIN_TYPE)
                                  .get();
        System.out.println("Status " + response.getStatus());
        System.out.println(response.readEntity(String.class));
        response.close();
    }

    public class LoggingFilter implements ClientRequestFilter {
        //private final Logger LOG = Logger.getLogger(LoggingFilter.class.getName());

        @Override
        public void filter(ClientRequestContext requestContext) throws IOException {
            //LOG.log(Logger.Level.INFO, requestContext.getEntity().toString());
            System.out.println(requestContext.getEntity().toString());
        }
    }

}
