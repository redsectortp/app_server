package com.whatnow.serverside.entrypoint;

import com.google.common.base.Strings;
import com.whatnow.serverside.database.FastData;
import com.whatnow.serverside.database.LongData;
import com.whatnow.serverside.login.UserEntry;
import com.whatnow.serverside.server.login.StaticDoLogin;
import com.whatnow.serverside.shared.login.BasicAuthenticationPair;
import javax.security.auth.login.FailedLoginException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import org.hibernate.Session;

/**
 *
 * @author Thiago
 */
@Path("/login")
public class Login extends Application {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String Presentation() {
        LongData a = new LongData();
        UserEntry b = new UserEntry();
        b.setEmail("a");
        b.setFullname("teste");
        b.setUsername("teste");
        b.setPassword("teste");
        Session dataSession = a.GetNewSession();
        dataSession.beginTransaction();
        dataSession.save(b);
        dataSession.getTransaction().commit();
        dataSession.close();
        return "saved";
    }

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_PLAIN)
    public Response do_login(MultivaluedMap<String, String> formParams) {
        ResponseBuilder response = Response.ok();
        String localUuid = "";
        BasicAuthenticationPair basicPair = new BasicAuthenticationPair();
        try {
            if (!Strings.isNullOrEmpty(formParams.getFirst("username"))) {
                basicPair.setUsername(formParams.getFirst("username"));
            }
            if (!Strings.isNullOrEmpty(formParams.getFirst("password"))) {
                basicPair.setPassword(formParams.getFirst("password"));
            }
        } catch (Exception ex) {
            response.status(Response.Status.BAD_REQUEST);
        }
        try {
            localUuid = StaticDoLogin.DoLogin(basicPair);
        } catch (FailedLoginException ex) {
            response.status(Response.Status.FORBIDDEN);
        }

        if (!Strings.isNullOrEmpty(localUuid)) {
            response.type(MediaType.TEXT_PLAIN);
            response.entity(localUuid);
        } else {
            response.status(Response.Status.INTERNAL_SERVER_ERROR);
        }
        return response.build();
    }
}
