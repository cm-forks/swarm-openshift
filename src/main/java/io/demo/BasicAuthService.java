package io.demo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

@Path("service")
public class BasicAuthService {

    private static List<String> users;

    static {
        String engineers = "Charles,Bruno,George,Sébastien,Clément,Heiko,Bob,Lance,Jay";
        users = Arrays.asList(engineers.split(","));
    }

    @GET
    @Path("echo")
    public String echo(@QueryParam("value") String value) {
        return value + " " + users.get((new Random()).nextInt(users.size()));
    }
}