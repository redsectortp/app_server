package org.rsrevival.scout.database;

import com.google.common.base.Strings;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;
import org.hibernate.service.ServiceRegistry;
import org.rsrevival.scout.database.login.UserSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SessionCache {

    static final Logger logger = LoggerFactory.getLogger(SessionCache.class);

    final private SessionFactory sessionFactory;

    public SessionCache() {
        Configuration configuration = new Configuration();
        configuration.configure("/hibernate-fastdata.cfg.xml");
        configuration.addAnnotatedClass(UserSession.class);
        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties()).build();
        sessionFactory = configuration.buildSessionFactory(serviceRegistry);
    }

    private Session GetNewSession() {
        return sessionFactory.openSession();
    }

    public void SaveSession(UserSession localUserSession) {
        Session fastSession = this.GetNewSession();
        fastSession.beginTransaction();
        fastSession.save(localUserSession);
        fastSession.getTransaction().commit();
        fastSession.close();
        fastSession = null;
    }

    public void RemoveSession(UserSession localUserSession) {
        Session fastSession = this.GetNewSession();
        fastSession.beginTransaction();
        fastSession.delete(localUserSession);
        fastSession.getTransaction().commit();
        fastSession.close();
        fastSession = null;
    }

    public void RemoveSession(String localUsername) {
        UserSession localUserSession = GetUserSession(localUsername);
        RemoveSession(localUserSession);
        localUserSession = null;
    }

    public UserSession GetUserSession(String localName) {
        if (Strings.isNullOrEmpty(localName)) {
            logger.error("localName argument is null or empty");
            throw new IllegalArgumentException();
        }
        UserSession localUserSession = new UserSession();
        Session fastSession = this.GetNewSession();
        Criteria criteria = fastSession.createCriteria(UserSession.class);
        criteria.add(Restrictions.eq("name", localName));
        try {
            logger.info("Entered try");
            List<UserSession> sessionList = criteria.list();
            for (UserSession entry : sessionList) {
                logger.info("Entered list of results from criteria,"
                        + " should be 1 result");
                localUserSession = entry;
            }
        } catch (Exception ex) {
        }
        return localUserSession;
    }
}
