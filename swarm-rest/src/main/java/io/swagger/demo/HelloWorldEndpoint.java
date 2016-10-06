package io.swagger.demo;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.wildfly.swarm.spi.runtime.annotations.ConfigurationValue;

@ApplicationScoped
@Path("/say")
public class HelloWorldEndpoint {

	private static List<String> users;

	static {
		String engineers = "Charles,Bruno,George,Sébastien,Clément,Heiko,Bob,Lance,Jay";
		users = Arrays.asList(engineers.split(","));
	}

	@Inject
	@ConfigurationValue("endpoint.hello.message")
	private Optional<String> message;

	@GET
	@Produces("text/plain")
	@Path("hello")
	public Response doGet() {
		return Response.ok("Response : " + message).build();
	}

	@GET
	@Path("echo")
	public String echo(@QueryParam("value") String value) {
		return Character.toUpperCase(value.charAt(0)) + value.substring(1) + " " + users.get((new Random()).nextInt(users.size()));
	}
}