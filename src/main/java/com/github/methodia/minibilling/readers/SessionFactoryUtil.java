package com.github.methodia.minibilling.readers;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class SessionFactoryUtil {

    public static Session getSession() {

            final SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();


        return sessionFactory.openSession();
    }
}
