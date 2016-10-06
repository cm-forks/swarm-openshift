package io.swarm.demo;

import java.io.File;
import java.util.Optional;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.ClassLoaderAsset;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.asset.FileAsset;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.wildfly.swarm.spi.api.JARArchive;
import org.wildfly.swarm.spi.runtime.annotations.ConfigurationValue;


@RunWith(Arquillian.class)
public class ConfigurationValueProducerTest {

    @Inject
    @ConfigurationValue("endpoint.hello.message")
    private Optional<String> message;

    @Deployment
    public static Archive<?> createDeployment() throws Exception {
        return ShrinkWrap.create(JARArchive.class, "arqDeployment.jar")
                .add(new FileAsset(new File("src/test/resources/project.yml")), "project.yml")
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @Test
    public void testServerAddressExists() {
        Assert.assertNotNull(message);
        Assert.assertEquals("DEBUG",message);
    }

}
