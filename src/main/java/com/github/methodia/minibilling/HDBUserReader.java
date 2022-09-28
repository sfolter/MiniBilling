package com.github.methodia.minibilling;

import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class HDBUserReader implements UsersReader {

    Session session;

    public HDBUserReader(final Session session) {
        this.session = session;
    }

    @Override
    public Map<String, User> read() {
        final Map<String, User> userMap = new LinkedHashMap<>();
        final Query<User> fromUser = session.createQuery("From User", User.class);
        final List<User> userList = fromUser.getResultList();
        final int counter = userList.size();
        for (int i = 1; i <= counter; i++) {
            for (final User user : userList) {
                if (Integer.parseInt(user.getRef()) == i) {
                    final String ref = user.getRef();
                    userMap.put(ref, user);
                }
            }
        }
        return userMap;
    }
}
