package io.swagger.demo;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Set;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.wildfly.swarm.Swarm;
import org.wildfly.swarm.cdi.CDIFraction;
import org.wildfly.swarm.jaxrs.JAXRSArchive;
import org.wildfly.swarm.jaxrs.JAXRSFraction;
import org.wildfly.swarm.logging.LoggingFraction;

//import org.jboss.logging.Logger;

public class RestServiceApp {
    public static void main(String[] args) throws Exception {

        // Logger logger = Logger.getLogger(RestServiceApp.class);

        URL cfg = Paths.get("/app/config/project.yml").toUri().toURL();
        assert cfg != null : "Failed to load stage configuration";

        System.out.println("Config URL : " + cfg.getFile());
        System.out.println("Content : " + Files.readAllLines(Paths.get("/app/config/project.yml")));

        Swarm swarm = new Swarm().withStageConfig(cfg);
        Set<String> keys = swarm.stageConfig().keys();
        for(String key : keys) {
            System.out.println("Key : " + key);
            System.out.println("Value : " + swarm.stageConfig().resolve(key).getValue());
        }

        swarm.fraction(new JAXRSFraction())
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
