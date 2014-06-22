package org.rsrevival.scout.entrypoint;

import javax.annotation.security.PermitAll;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
/**
 *
 * @author Thiago
 */

@Path("/")
@PermitAll
public class General {
    
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String Presentation() {
        return "oh hi";
    }
    
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/{entrada}")
    public String Sudo(@PathParam("entrada") String content) {
        return content;
    }
    
    
}
