package com.github.methodia.minibilling;

import java.util.TreeMap;

/**
 * @author Miroslav Kovachev
 * 28.07.2022
 * Methodia Inc.
 */
public interface UsersReader {

    TreeMap<String, User> read();


}
