package com.whatnow.serverside.server.login;

import com.eaio.uuid.UUID;
import com.google.common.base.Strings;
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

/**
 *
 * @author Thiago
 */
public class StaticDoLogin {

    public static String DoLogin(BasicAuthenticationPair basicPair) throws
            FailedLoginException {
        String localUuid = "";

        LongData longAccess = new LongData();
        Session longSession = longAccess.GetNewSession();

        FastData fastAccess = new FastData();
        Session fastSession = fastAccess.GetNewSession();

        Criteria criteria = longSession.createCriteria(UserEntry.class);
        criteria.add(Restrictions.eq("username", basicPair.getUsername()));

        try {
            List<UserEntry> entryList = criteria.list();
            for (UserEntry entry : entryList) {
                localUuid = new UUID().toString();
                UserSession localUserSession = new UserSession();
                localUserSession.setName(basicPair.getUsername());
                localUserSession.setToken(localUuid);
                fastSession.beginTransaction();
                fastSession.save(localUserSession);
                fastSession.getTransaction().commit();
                fastSession.close();
                longSession.close();
            }
        } catch (Exception ex) {
            throw new FailedLoginException();
        }

        return localUuid;
    }
}
