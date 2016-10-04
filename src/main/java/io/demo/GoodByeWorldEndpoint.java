package io.demo;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Path("/bye")
public class GoodByeWorldEndpoint {

	@GET
	@Produces("text/plain")
	public Response doGet() {
		return Response.ok("Good bye from WildFly Swarm!").build();
	}
}