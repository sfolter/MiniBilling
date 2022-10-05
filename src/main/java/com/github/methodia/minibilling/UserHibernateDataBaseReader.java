package com.github.methodia.minibilling;

import org.hibernate.Session;
import org.hibernate.query.Query;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class UserHibernateDataBaseReader implements UsersReader {

    Session session;

    public UserHibernateDataBaseReader(final Session session) {
        this.session = session;
    }

    @Override
    public Map<String, User> read() {
        final Map<String, User> userMap = new LinkedHashMap<>();
        final Query<User> fromUser = session.createQuery("From User", User.class);
        final List<User> userList = fromUser.getResultList();
        for (final User user : userList) {
            String ref = user.getRef();
            userMap.put(ref, user);
        }
        return userMap;
    }
}