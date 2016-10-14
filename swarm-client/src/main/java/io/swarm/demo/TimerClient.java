package io.swarm.demo;

import javax.inject.Inject;

import org.apache.camel.Endpoint;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.cdi.Uri;

public class TimerClient extends RouteBuilder {

    @Inject
    @Uri("timer:foo?period=5000")
    private Endpoint inputEndpoint;

    @Inject
    @Uri("netty4-http:http://{{service:swarm-rest:localhost:8080}}/say/hello?keepAlive=false&disconnect=true")
    private Endpoint httpEndpoint;

    @Inject
    @Uri("log:output?showExchangePattern=false&showBodyType=false&showStreams=true")
    private Endpoint resultEndpoint;

    @Override
    public void configure() throws Exception {

        // let the client attempt to redeliver if the service is not available
        onException(Exception.class)
          .maximumRedeliveries(5).redeliveryDelay(1000);

        from(inputEndpoint)
          .to(httpEndpoint)
          .setBody()
            .simple("${body} - ${header.CamelTimerFiredTime}")
          .to(resultEndpoint);
    }

}
