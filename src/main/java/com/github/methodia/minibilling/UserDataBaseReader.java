package com.github.methodia.minibilling;

import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;
import java.util.TreeMap;

public class UserDataBaseReader implements UsersReader {

    Session session;

    public UserDataBaseReader(final Session session) {
        this.session = session;
    }

    @Override
    public TreeMap<String, User> read() {
        final TreeMap<String, User> userMap = new TreeMap<>();
        final Query<User> fromUser = session.createQuery("From User", User.class);
        final List<User> userList = fromUser.getResultList();
        for (final User user : userList) {
            String ref = user.getRef();
            userMap.put(ref, user);
        }
        return userMap;
    }
}
