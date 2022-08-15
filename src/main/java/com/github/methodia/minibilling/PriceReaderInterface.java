package com.github.methodia.minibilling;

import java.util.List;
import java.util.Map;

public interface PriceReaderInterface {
    Map<Integer, List<Price>> read();

}
