package com.github.methodia.minibilling;

import java.util.Collection;
import java.util.List;

public interface ReadingsReader {
    List<Reading> read(String directory,String borderTime);
}
