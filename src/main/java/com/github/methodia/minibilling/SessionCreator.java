package com.github.methodia.minibilling;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class SessionCreator {

    private SessionCreator() {
    }

    public static Session createSession() {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
    return sessionFactory.openSession();
    }
}
