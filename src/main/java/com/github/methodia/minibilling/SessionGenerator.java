package com.github.methodia.minibilling;

/**
 * @author Todor Todorov
 * @Date 23.09.2022
 * Methodia Inc.
 */

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class SessionGenerator {

    public Session createSession() {
        final SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        final Session session = sessionFactory.openSession();
        return session;
    }
}
