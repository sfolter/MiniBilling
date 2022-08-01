package com.github.methodia.minibilling;

import java.util.Collection;
import java.util.Map;

public interface PricesReader {
    Map<Integer, Collection<Price>> read();
}
