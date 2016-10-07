package io.swarm.demo;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.wildfly.swarm.spi.runtime.annotations.ConfigurationValue;

@ApplicationScoped
@Path("/say")
@Api(value = "say", description = "Endpoint for Say operations")
public class HelloWorldEndpoint {

	private static List<String> users;

	static {
		String engineers = "Charles,Bruno,George,Sébastien,Clément,Heiko,Bob,Lance,Jay";
		users = Arrays.asList(engineers.split(","));
	}

	@Inject
	@ConfigurationValue("endpoint.hello.message")
	String message;

	@GET
	@Produces("text/plain")
	@Path("hello")
	@ApiOperation(value = "Returns hello details", notes = "Returns hello details.", response = String.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successful generated response message", response = String.class),
			@ApiResponse(code = 500, message = "Internal server error") }
	)
	public Response doGet() {
		System.out.println("Message : " + message);
		return Response.ok(message + " from : " + System.getenv("HOSTNAME")).build();
	}

	@GET
	@Path("echo")
	@ApiOperation(value = "Returns echo details", notes = "Returns echo details.", response = String.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successful echoing", response = String.class),
			@ApiResponse(code = 500, message = "Internal server error") }
	)
	public String echo(/*@ApiParam(value = "value to be passed", required = true)*/ @QueryParam("value") String value) {
		return Character.toUpperCase(value.charAt(0)) + value.substring(1) + " " + users.get((new Random()).nextInt(users.size()));
	}
}