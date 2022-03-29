package it.polimi.db2.db2project.examples;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("/hello-world")
public class HelloResource {
    @GET
    @Produces("text/plain")
    public String hello() {
        return "Hello, World!";
    }
}

// IMPORTANT - After you include a new dependency in the pom.xml file, you need to include it in the artifact too!