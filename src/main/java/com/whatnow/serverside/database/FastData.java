package com.whatnow.serverside.database;

import com.eaio.uuid.UUID;
import com.whatnow.serverside.login.UserSession;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class FastData {

    final private SessionFactory sessionFactory;
    final private Session session;

    public FastData() {
        Configuration configuration = new Configuration();
        configuration.configure("/hibernate-fastdata.cfg.xml");
        configuration.addAnnotatedClass(UserSession.class);
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

    public void QuickFill() {
        UserSession user = new UserSession(1, "Levi", new UUID());
        session.beginTransaction();
        session.save(user);
        session.getTransaction().commit();
        user = new UserSession(2, "Levi2", new UUID());
        session.beginTransaction();
        session.save(user);
        session.getTransaction().commit();
    }

    public String QuickRead() {
        String a = "";
        session.beginTransaction();
        List result = session.createQuery("from UserSession").list();
        for (UserSession event : (List<UserSession>) result) {
            a = a.concat(event.getName());
            a = a.concat(":");
            a = a.concat(String.valueOf(event.getId()));
            a = a.concat(":");
            a = a.concat(event.getToken().toString());
        }
        session.getTransaction().commit();
        session.close();
        return a;
    }
}
