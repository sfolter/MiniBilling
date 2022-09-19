package com.github.methodia.minibilling.readers;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class SessionFactoryUtil {

    public static Session getSessionFactory() {
        Session session = null;
        try {
            final SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
            session = sessionFactory.openSession();
        } catch (Exception e) {
            System.out.println(e);
        }
        return session;
    }
}
