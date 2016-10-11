package io.swarm.demo;

import javax.ws.rs.NotAuthorizedException;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.representations.AccessTokenResponse;

public class ClientPolicy {

    public static void main(String[] args) {
        AccessTokenResponse token = null;
        try {
            Keycloak keycloak = KeycloakBuilder.builder()
                    .serverUrl("http://localhost:8080/auth")
                    .realm("basic-auth")
                    .clientId("basic-auth-service")
                    .username("user")
                    .password("passwordd")
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

    }
}
