package com.github.methodia.minibilling.readers;

import com.github.methodia.minibilling.entity.User;

import java.util.Map;

public interface UsersReader {

    Map<String, User> read();

}
