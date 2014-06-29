package org.rsrevival.scout.server.login;

import com.eaio.uuid.UUID;
import org.rsrevival.scout.database.SessionCache;
import org.rsrevival.scout.database.LongData;
import org.rsrevival.scout.database.login.UserEntry;
import org.rsrevival.scout.database.login.UserSession;
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

        SessionCache fastAccess = new SessionCache();

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
                fastAccess.SaveSession(localUserSession);
                longSession.close();
            }
            logger.info("Criteria list for block just finalized");
        } catch (Exception ex) {
            logger.error("FailedLoginException on the criteria try: "
                    .concat(ex.toString()));
            throw new FailedLoginException();
        }
        fastAccess = null;
        longSession = null;
        localUuid = null;
        return localUuid;
    }

    public static Boolean DoLogout(UserSession localUserSession) {
        Boolean result = false;
        logger.info("Stating logout procedure for user: "
                .concat(localUserSession.getName()));

        SessionCache fastSession = new SessionCache();
        
        logger.info("Access and session created");
        try {
            fastSession.RemoveSession(localUserSession);
            result = true;
        } catch (Exception ex) {
            logger.error("Exception on the delete try: "
                    .concat(ex.toString()));
        }
        return result;
    }
}
