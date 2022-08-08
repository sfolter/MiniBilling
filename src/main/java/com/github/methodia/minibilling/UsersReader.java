package com.github.methodia.minibilling;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author Miroslav Kovachev
 * 28.07.2022
 * Methodia Inc.
 */
public interface UsersReader {

    List<User> read() throws IOException;

}
