package com.github.methodia.minibilling;

import java.io.IOException;
import java.util.List;

public interface UsersReader {

    List<User> read() throws IOException;

}
