package com.github.methodia.minibilling.dataRead;

import com.github.methodia.minibilling.entityClasses.User;

import java.util.List;

public interface DataReader {

    List<User> read();
}
