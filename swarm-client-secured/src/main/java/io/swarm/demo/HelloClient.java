package io.swarm.demo;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.wildfly.swarm.Swarm;
import org.wildfly.swarm.jaxrs.JAXRSArchive;
import org.wildfly.swarm.jaxrs.JAXRSFraction;

public class HelloClient {
    public static void main(String[] args) throws Exception {
        Swarm swarm = new Swarm();
        swarm.fraction(new JAXRSFraction());
        JAXRSArchive archive = ShrinkWrap.create(JAXRSArchive.class);
        archive.addClass(HelloClientEndpoint.class);
        swarm.start().deploy();
    }
}
