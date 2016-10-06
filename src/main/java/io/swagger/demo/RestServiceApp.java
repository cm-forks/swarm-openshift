package io.swagger.demo;

import java.net.URL;
import java.nio.file.Paths;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.wildfly.swarm.Swarm;
import org.wildfly.swarm.cdi.CDIFraction;
import org.wildfly.swarm.jaxrs.JAXRSArchive;
import org.wildfly.swarm.jaxrs.JAXRSFraction;
import org.wildfly.swarm.logging.LoggingFraction;

public class RestServiceApp {
    public static void main(String[] args) throws Exception {

        URL cfg = Paths.get("/app/config/project.yml").toUri().toURL();
        assert cfg != null : "Failed to load stage configuration";

        Swarm swarm = new Swarm().withStageConfig(cfg);
        swarm
                .fraction(new JAXRSFraction())
                .fraction(new CDIFraction())
                .fraction(new LoggingFraction());

        // Start the container
        swarm.start();

        JAXRSArchive appDeployment = ShrinkWrap.create(JAXRSArchive.class);
        appDeployment.addResource(HelloWorldEndpoint.class);

        // Deploy your app
        swarm.deploy(appDeployment);
    }
}
