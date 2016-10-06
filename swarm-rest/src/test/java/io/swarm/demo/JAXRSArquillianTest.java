package io.swarm.demo;

import java.io.File;
import java.net.URL;

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
import org.wildfly.swarm.Swarm;
import org.wildfly.swarm.arquillian.CreateSwarm;
import org.wildfly.swarm.cdi.CDIFraction;
import org.wildfly.swarm.jaxrs.JAXRSArchive;
import org.wildfly.swarm.jaxrs.JAXRSFraction;
import org.wildfly.swarm.logging.LoggingFraction;

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

    /* CAN'T BE USED
    2016-10-06 19:39:18,788 ERROR [org.jboss.msc.service.fail] (MSC service thread 1-4) MSC000001: Failed to start service jboss.deployment.unit."myapp.war".WeldStartService: org.jboss.msc.service.StartException in service jboss.deployment.unit."myapp.war".WeldStartService: Failed to start service
        at org.jboss.msc.service.ServiceControllerImpl$StartTask.run(ServiceControllerImpl.java:1904)
        at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1142)
        at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:617)
        at java.lang.Thread.run(Thread.java:745)
Caused by: org.jboss.weld.exceptions.DeploymentException: WELD-001408: Unsatisfied dependencies for type String with qualifiers @ConfigurationValue
  at injection point [BackedAnnotatedField] @Inject @ConfigurationValue io.swarm.demo.HelloWorldEndpoint.message
  at io.swarm.demo.HelloWorldEndpoint.message(HelloWorldEndpoint.java:0)

        at org.jboss.weld.bootstrap.Validator.validateInjectionPointForDeploymentProblems(Validator.java:359)
        at org.jboss.weld.bootstrap.Validator.validateInjectionPoint(Validator.java:281)
        at org.jboss.weld.bootstrap.Validator.validateGeneralBean(Validator.java:134)
        at org.jboss.weld.bootstrap.Validator.validateRIBean(Validator.java:155)
        at org.jboss.weld.bootstrap.Validator.validateBean(Validator.java:518)
        at org.jboss.weld.bootstrap.ConcurrentValidator$1.doWork(ConcurrentValidator.java:68)
        at org.jboss.weld.bootstrap.ConcurrentValidator$1.doWork(ConcurrentValidator.java:66)
        at org.jboss.weld.executor.IterativeWorkerTaskFactory$1.call(IterativeWorkerTaskFactory.java:63)
        at org.jboss.weld.executor.IterativeWorkerTaskFactory$1.call(IterativeWorkerTaskFactory.java:56)
        at java.util.concurrent.FutureTask.run(FutureTask.java:266)
        at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1142)
        at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:617)
        at java.lang.Thread.run(Thread.java:745)
        at org.jboss.threads.JBossThread.run(JBossThread.java:320)

2016-10-06 19:39:18,793 ERROR [org.jboss.as.controller.management-operation] (main) WFLYCTL0013: Operation ("add") failed - address: (("deployment" => "myapp.war")) - failure description: {
    "WFLYCTL0080: Failed services" => {"jboss.deployment.unit.\"myapp.war\".WeldStartService" => "org.jboss.msc.service.StartException in service jboss.deployment.unit.\"myapp.war\".WeldStartService: Failed to start service
    Caused by: org.jboss.weld.exceptions.DeploymentException: WELD-001408: Unsatisfied dependencies for type String with qualifiers @ConfigurationValue
  at injection point [BackedAnnotatedField] @Inject @ConfigurationValue io.swarm.demo.HelloWorldEndpoint.message
  at io.swarm.demo.HelloWorldEndpoint.message(HelloWorldEndpoint.java:0)
"},
    "WFLYCTL0412: Required services that are not installed:" => ["jboss.deployment.unit.\"myapp.war\".WeldStartService"],
    "WFLYCTL0180: Services with missing/unavailable dependencies" => undefined
}
2016-10-06 19:39:18,794 ERROR [org.jboss.as.server] (main) WFLYSRV0021: Deploy of deployment "myapp.war" was rolled back with the following failure message:
{
    "WFLYCTL0080: Failed services" => {"jboss.deployment.unit.\"myapp.war\".WeldStartService" => "org.jboss.msc.service.StartException in service jboss.deployment.unit.\"myapp.war\".WeldStartService: Failed to start service
    Caused by: org.jboss.weld.exceptions.DeploymentException: WELD-001408: Unsatisfied dependencies for type String with qualifiers @ConfigurationValue
  at injection point [BackedAnnotatedField] @Inject @ConfigurationValue io.swarm.demo.HelloWorldEndpoint.message
  at io.swarm.demo.HelloWorldEndpoint.message(HelloWorldEndpoint.java:0)
"},
    "WFLYCTL0412: Required services that are not installed:" => ["jboss.deployment.unit.\"myapp.war\".WeldStartService"],
    "WFLYCTL0180: Services with missing/unavailable dependencies" => undefined
}

    @CreateSwarm
    public static Swarm newContainer() throws Exception {
        URL cfg = JAXRSArquillianTest.class.getClassLoader().getResource("project-stages.yml");
        System.out.println("URL : " + cfg.getFile());
        return new Swarm()
                .withStageConfig(cfg)
                .fraction(new JAXRSFraction())
                .fraction(new CDIFraction())
                .fraction(LoggingFraction.createDebugLoggingFraction());
    }
    */

    @Test
    @RunAsClient
    public void testResource() {
        // Call the /say/hello service and checks that we get the correct response
        Response response = getUrlContents("http://localhost:8080/say/hello");
        Assert.assertTrue(response.getBody().contains("Hello from WildFly Swarm running on OpenShift!"));
    }
}
