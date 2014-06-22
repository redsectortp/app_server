package org.rsrevival.scout.database;

import org.rsrevival.scout.login.UserEntry;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class LongData {

    final private SessionFactory sessionFactory;
    final private Session session;

    public LongData() {
        Configuration configuration = new Configuration();
        configuration.configure("/hibernate-longdata.cfg.xml");
        configuration.addAnnotatedClass(UserEntry.class);
        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
        sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        session = sessionFactory.openSession();
    }

    public Session GetCurrentSession() {
        return session;
    }

    public Session GetNewSession() {
        return sessionFactory.openSession();
    }
}
