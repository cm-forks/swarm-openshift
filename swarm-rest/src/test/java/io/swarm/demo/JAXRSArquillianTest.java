package io.swarm.demo;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.ClassLoaderAsset;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.wildfly.swarm.Swarm;
import org.wildfly.swarm.arquillian.CreateSwarm;
import org.wildfly.swarm.cdi.CDIFraction;
import org.wildfly.swarm.jaxrs.JAXRSArchive;
import org.wildfly.swarm.jaxrs.JAXRSFraction;

@RunWith(Arquillian.class)
public class JAXRSArquillianTest extends SimpleHttp {

    @Deployment(testable = false)
    public static Archive createDeployment() throws Exception {
        JAXRSArchive deployment = ShrinkWrap.create(JAXRSArchive.class, "myapp.war");
        deployment.add(new ClassLoaderAsset("project.yml", JAXRSArquillianTest.class.getClassLoader()), "project.yml");
        deployment.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
        deployment.addClass(HelloWorldEndpoint.class);
        deployment.setContextRoot("rest");
        deployment.addAllDependencies();
        return deployment;
    }

    @CreateSwarm
    public static Swarm newContainer() throws Exception {
        return new Swarm()
                .fraction(new JAXRSFraction())
                .fraction(new CDIFraction());
    }

    @Test
    @RunAsClient
    public void testResource() {
        // verify indirect access to secure resources
        Response response = getUrlContents("http://localhost:8080/say/hello");
        Assert.assertTrue(response.getBody().contains("Hello from WildFly Swarm running on OpenShift!"));
    }
}
