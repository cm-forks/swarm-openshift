package io.swarm.demo;

import java.util.Optional;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.ClassLoaderAsset;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.wildfly.swarm.spi.api.JARArchive;
import org.wildfly.swarm.spi.runtime.annotations.ConfigurationValue;


@RunWith(Arquillian.class)
public class ConfigurationValueTest {

    @Inject
    @ConfigurationValue("service.hello.message")
    private Optional<String> message;

    @Deployment
    public static Archive<?> createDeployment() throws Exception {
        return ShrinkWrap.create(JARArchive.class, "arq-test.jar")
                .add(new ClassLoaderAsset("project-stages.yml", ConfigurationValueTest.class.getClassLoader()), "project-stages.yml")
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    /*  GENERATE THE CDI ERROR IF WE ADD @CreateSwarm
    2016-10-06 18:55:05,736 ERROR [stderr] (main) Caused by: org.wildfly.swarm.container.DeploymentException: WFSWARM0007: Deployment failed: {"WFLYCTL0080: Failed services" => {"jboss.deployment.unit.\"arq-test.jar\".WeldStartService" => "org.jboss.msc.service.StartException in service jboss.deployment.unit.\"arq-test.jar\".WeldStartService: Failed to start service
    2016-10-06 18:55:05,736 ERROR [stderr] (main)     Caused by: org.jboss.weld.exceptions.DeploymentException: WELD-001408: Unsatisfied dependencies for type Optional<String> with qualifiers @ConfigurationValue
    2016-10-06 18:55:05,736 ERROR [stderr] (main)   at injection point [BackedAnnotatedField] @Inject @ConfigurationValue private io.swarm.demo.ConfigurationValueTest.message
    2016-10-06 18:55:05,737 ERROR [stderr] (main)   at io.swarm.demo.ConfigurationValueTest.message(ConfigurationValueTest.java:0)
    2016-10-06 18:55:05,737 ERROR [stderr] (main) "},"WFLYCTL0412: Required services that are not installed:" => ["jboss.deployment.unit.\"arq-test.jar\".WeldStartService"],"WFLYCTL0180: Services with missing/unavailable dependencies" => undefined}

    @CreateSwarm
    public static Swarm newContainer() throws Exception {
        return new Swarm().fraction(new CDIFraction());
    }*/

    @Test
    public void testHelloMessageProperty() {
        Assert.assertNotNull(message);
        Assert.assertEquals("Hello from WildFly Swarm running on OpenShift!",message.get());
    }

}
