package com.github.methodia.minibilling;

import java.util.List;
import java.util.Map;

public interface ReadingsReader {

    Map<String, List<Reading>> read();
}
