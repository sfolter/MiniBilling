package com.github.methodia.minibilling.readers;

import com.github.methodia.minibilling.entity.Reading;

import java.util.List;
import java.util.Map;

public interface ReadingsReader {

    Map<String, List<Reading>> read();
}
