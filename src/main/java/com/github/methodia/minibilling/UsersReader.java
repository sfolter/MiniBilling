package com.github.methodia.minibilling;

import java.io.IOException;
import java.util.List;

/**
 * @author Miroslav Kovachev
 * 28.07.2022
 * Methodia Inc.
 */
public interface UsersReader {

    List<User> read() throws IOException;

}
