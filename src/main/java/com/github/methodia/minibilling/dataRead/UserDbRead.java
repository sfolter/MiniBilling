package com.github.methodia.minibilling.dataRead;

import com.github.methodia.minibilling.entityClasses.User;
import org.hibernate.Session;

import javax.persistence.Query;
import java.util.List;

public class UserDbRead implements DataReader {

    Session session;

    public UserDbRead(final Session session) {
        this.session = session;
    }

    @Override
    public List<User> read() {

        session.beginTransaction();
        final Query query = session.createQuery("from User");
        return query.getResultList();
    }
}
