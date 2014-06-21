package com.whatnow.serverside.server.login;

import com.eaio.uuid.UUID;
import com.whatnow.serverside.database.FastData;
import com.whatnow.serverside.database.LongData;
import com.whatnow.serverside.login.UserEntry;
import com.whatnow.serverside.login.UserSession;
import com.whatnow.serverside.shared.login.BasicAuthenticationPair;
import java.util.List;
import javax.security.auth.login.FailedLoginException;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Thiago
 */
public class StaticDoLogin {

    static final Logger logger = LoggerFactory.getLogger(StaticDoLogin.class);

    public static String DoLogin(BasicAuthenticationPair basicPair) throws
            FailedLoginException {
        logger.info("Starting do_login procedure for ".
                concat(basicPair.getUsername()));
        String localUuid = "";

        LongData longAccess = new LongData();
        Session longSession = longAccess.GetNewSession();

        FastData fastAccess = new FastData();
        Session fastSession = fastAccess.GetNewSession();

        Criteria criteria = longSession.createCriteria(UserEntry.class);
        criteria.add(Restrictions.eq("username", basicPair.getUsername()));
        logger.info("Accesses, sessions and criteria created");
        try {
            logger.info("Entered try");
            List<UserEntry> entryList = criteria.list();
            for (UserEntry entry : entryList) {
                logger.info("Entered list of results from criteria,"
                        + " should be 1 result");
                localUuid = new UUID().toString();
                UserSession localUserSession = new UserSession();
                localUserSession.setName(entry.getUsername());
                localUserSession.setToken(localUuid);
                fastSession.beginTransaction();
                fastSession.save(localUserSession);
                fastSession.getTransaction().commit();
                fastSession.close();
                longSession.close();
            }
            logger.info("Criteria list for block just finalized");
        } catch (Exception ex) {
            logger.error("FailedLoginException on the criteria try: "
                    .concat(ex.toString()));
            throw new FailedLoginException();
        }

        return localUuid;
    }
}
