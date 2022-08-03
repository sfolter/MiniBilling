package com.github.methodia.minibilling;

import java.util.Collection;

public interface ReadingsReader {
    Collection<Reading> read(String directory);
}
