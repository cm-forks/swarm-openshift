package io.swarm.demo;

import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.ClassLoaderAsset;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.asset.FileAsset;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.wildfly.swarm.Swarm;
import org.wildfly.swarm.arquillian.CreateSwarm;
import org.wildfly.swarm.cdi.CDIFraction;
import org.wildfly.swarm.jaxrs.JAXRSArchive;
import org.wildfly.swarm.jaxrs.JAXRSFraction;
import org.wildfly.swarm.logging.LoggingFraction;

@RunWith(Arquillian.class)
public class JAXRSArquillianTest extends SimpleHttp {

    @Deployment(testable = false)
    public static Archive createDeployment() throws Exception {
        JAXRSArchive deployment = ShrinkWrap.create(JAXRSArchive.class, "myapp.war");
        deployment.add(new FileAsset(new File("src/test/resources/project.yml")),"project.yml");
        deployment.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
        deployment.addClass(HelloWorldEndpoint.class);
        deployment.addAllDependencies();
        return deployment;
    }

    @CreateSwarm
    public static Swarm newContainer() throws Exception {
        URL cfg = JAXRSArquillianTest.class.getClassLoader().getResource("project.yml");
        System.out.println("URL : " + cfg.getFile());
        return new Swarm()
                .withStageConfig(cfg)
                .fraction(new JAXRSFraction())
                .fraction(new CDIFraction())
                .fraction(LoggingFraction.createDebugLoggingFraction());
    }

    @Test
    @RunAsClient
    public void testResource() {
        // verify indirect access to secure resources
        Response response = getUrlContents("http://localhost:8080/say/hello");
        Assert.assertTrue(response.getBody().contains("Hello from WildFly Swarm running on OpenShift!"));
    }
}
