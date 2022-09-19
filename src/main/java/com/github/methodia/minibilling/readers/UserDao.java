package com.github.methodia.minibilling.readers;

import com.github.methodia.minibilling.entity.User;
import org.hibernate.Session;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class UserDao implements UsersReader {

    @Override
    public Map<String, User> read() {
        try (final Session session = SessionFactoryUtil.getSessionFactory()) {
            List<User> usersDao = session.createQuery("from User ", User.class).getResultList();
            return usersDao.stream()
                    .collect(Collectors.toMap(User::getRef, user -> user));
        }
    }
}
