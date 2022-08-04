package com.github.methodia.minibilling;

import java.util.Collection;
import java.util.Map;

/**
 * @author Miroslav Kovachev
 * 28.07.2022
 * Methodia Inc.
 */
public interface UsersReader {

    Map<String,User> read();

}
