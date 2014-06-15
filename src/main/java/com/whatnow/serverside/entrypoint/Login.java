package com.whatnow.serverside.entrypoint;

import com.whatnow.serverside.database.FastData;
import javax.annotation.security.PermitAll;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
/**
 *
 * @author Thiago
 */

@Path("/login")
@PermitAll
public class Login extends Application {
    
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String Presentation() {
        FastData a = new FastData();
        a.QuickFill();
        return a.QuickRead();
    }
}
