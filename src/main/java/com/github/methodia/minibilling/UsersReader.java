package com.github.methodia.minibilling;

import java.util.Collection;
import java.util.Map;

public interface UsersReader {
    Map<String,User> read(String directory);

}
