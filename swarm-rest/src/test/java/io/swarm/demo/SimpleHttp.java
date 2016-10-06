package io.swarm.demo;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;

public class SimpleHttp {

    protected Response getUrlContents(String theUrl) {
        return getUrlContents(theUrl, true);
    }

    protected Response getUrlContents(String theUrl, boolean useAuth) {
        return getUrlContents(theUrl, useAuth, true);
    }

    protected Response getUrlContents(String theUrl, boolean useAuth, boolean followRedirects) {

        StringBuilder content = new StringBuilder();
        int code;

        try {

            CredentialsProvider provider = new BasicCredentialsProvider();
            UsernamePasswordCredentials credentials = new UsernamePasswordCredentials("admin", "password");
            provider.setCredentials(AuthScope.ANY, credentials);

            HttpClientBuilder builder = HttpClientBuilder.create();
            if (!followRedirects) builder.disableRedirectHandling();
            if (useAuth) builder.setDefaultCredentialsProvider(provider);
            HttpClient client = builder.build();

            HttpResponse response = client.execute(new HttpGet(theUrl));
            code = response.getStatusLine().getStatusCode();

            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(response.getEntity().getContent())
            );

            String line;

            while ((line = bufferedReader.readLine()) != null) {
                content.append(line + "\n");
            }
            bufferedReader.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return new Response(code, content.toString());
    }

    public class Response {
        public Response(int status, String body) {
            this.status = status;
            this.body = body;
        }

        public int getStatus() {
            return status;
        }

        public String getBody() {
            return body;
        }

        int status;

        String body;
    }
}
