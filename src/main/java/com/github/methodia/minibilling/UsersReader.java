package com.github.methodia.minibilling;

import java.util.Collection;

public interface UsersReader {
    Collection<User> read(String directory);

}
