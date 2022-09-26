package com.github.methodia.minibilling;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public final class SessionCreator {

    private SessionCreator() {
    }

    public static Session createSession() {
        final SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        return sessionFactory.openSession();
    }
}
