package io.swarm.demo;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Set;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.wildfly.swarm.Swarm;
import org.wildfly.swarm.cdi.CDIFraction;
import org.wildfly.swarm.jaxrs.JAXRSArchive;
import org.wildfly.swarm.jaxrs.JAXRSFraction;
import org.wildfly.swarm.keycloak.KeycloakFraction;
import org.wildfly.swarm.keycloak.Secured;
import org.wildfly.swarm.logging.LoggingFraction;
import org.wildfly.swarm.undertow.WARArchive;
import org.wildfly.swarm.undertow.descriptors.WebXmlAsset;

public class RestServiceApp {
    public static void main(String[] args) throws Exception {

        String cfg_path = System.getenv("PROJECT_PATH");
        cfg_path = (cfg_path == null) ? "/app/config/project.yml" : cfg_path;

        URL cfg = Paths.get(cfg_path).toUri().toURL();
        assert cfg != null : "Failed to load stage configuration";

        System.out.println("Config URL : " + cfg.getFile());
        System.out.println("Content : " + Files.readAllLines(Paths.get(cfg_path)));

        Swarm swarm = new Swarm(false).withStageConfig(cfg);
        Set<String> keys = swarm.stageConfig().keys();
        for(String key : keys) {
            System.out.println("Key : " + key);
            System.out.println("Value : " + swarm.stageConfig().resolve(key).getValue());
        }

        swarm.fraction(new JAXRSFraction())
             .fraction(new CDIFraction())
             .fraction(new LoggingFraction())
             .fraction(new KeycloakFraction());

        // Start the container
        swarm.start();

        // Create the archive and register the resources to be packaged/scanned
        JAXRSArchive archive = ShrinkWrap.create(JAXRSArchive.class);
        archive.add(new WebXmlAsset());
        archive.addAsResource("WEB-INF/keycloak.json","keycloak.json");
        archive.as(Secured.class);
        archive.addClass(HelloWorldEndpoint.class);

        // Deploy the archive
        swarm.deploy(archive);
    }
}
