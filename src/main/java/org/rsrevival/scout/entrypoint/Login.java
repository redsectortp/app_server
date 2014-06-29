package org.rsrevival.scout.entrypoint;

import com.google.common.base.Strings;
import org.rsrevival.scout.database.LongData;
import org.rsrevival.scout.database.login.UserEntry;
import org.rsrevival.scout.server.login.StaticDoLogin;
import org.rsrevival.scout.server.login.BasicAuthenticationPair;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Thiago
 */
@Path("/login")
public class Login extends Application {

    static final Logger logger = LoggerFactory.getLogger(Login.class);

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
        logger.info("Received REST do_login request");
        ResponseBuilder response = Response.ok();
        String localUuid = "";
        BasicAuthenticationPair basicPair = new BasicAuthenticationPair();
        try {
            logger.info("Filling basicPair");
            if (!Strings.isNullOrEmpty(formParams.getFirst("username"))) {
                basicPair.setUsername(formParams.getFirst("username"));
                logger.info("basicPair username: "
                        .concat(basicPair.getUsername()));
            }
            if (!Strings.isNullOrEmpty(formParams.getFirst("password"))) {
                basicPair.setPassword(formParams.getFirst("password"));
                logger.info("basicPair password length: "
                        .concat(String.valueOf(
                                        basicPair.getPassword().length())));
            }
        } catch (Exception ex) {
            logger.error("Exception in basicPair try: "
                    .concat(ex.toString()));
            response.status(Response.Status.BAD_REQUEST);
        }
        try {
            logger.info("Fetching localUuid from StaticDoLogin");
            localUuid = StaticDoLogin.DoLogin(basicPair);
        } catch (FailedLoginException ex) {
            logger.info("StaticDoLogin exception: ".concat(ex.toString()));
            response.status(Response.Status.FORBIDDEN);
        }

        if (!Strings.isNullOrEmpty(localUuid)) {
            logger.info("Setting response");
            response.type(MediaType.TEXT_PLAIN);
            response.entity(localUuid);
        } else {
            logger.error("localUuid is null or empty");
            response.status(Response.Status.INTERNAL_SERVER_ERROR);
        }
        logger.info("Response ready to be built and sent");
        return response.build();
    }
}
