package io.swarm.demo;

import java.io.File;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.asset.FileAsset;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.wildfly.swarm.jaxrs.JAXRSArchive;

@RunWith(Arquillian.class)
@RunAsClient
public class JAXRSArquillianTest extends SimpleHttp {

    @Deployment(testable = false)
    public static Archive createDeployment() throws Exception {
        JAXRSArchive deployment = ShrinkWrap.create(JAXRSArchive.class, "myapp.war");
        deployment.add(new FileAsset(new File("src/test/resources/project.yml")),"project-stages.yml");
        deployment.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
        deployment.addClass(HelloWorldEndpoint.class);
        return deployment;
    }

    /*
    @CreateSwarm
    public static Swarm newContainer() throws Exception {
        URL cfg = JAXRSArquillianTest.class.getClassLoader().getResource("project-stages.yml");
        System.out.println("URL : " + cfg.getFile());
        return new Swarm()
                .withStageConfig(cfg)
                .fraction(new JAXRSFraction())
                .fraction(LoggingFraction.createDebugLoggingFraction());
    }
    */

    @Test
    public void testResource() {
        // Call the /say/hello service and checks that we get the correct response
        Response response = getUrlContents("http://localhost:8080/say/hello");
        Assert.assertTrue(response.getBody().contains("Hello from WildFly Swarm running on OpenShift!"));
    }
}
