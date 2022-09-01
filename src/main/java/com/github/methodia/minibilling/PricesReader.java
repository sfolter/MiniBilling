package com.github.methodia.minibilling;


import java.util.List;
import java.util.Map;

public interface PricesReader {

    Map<Integer, List<Price>> read();

}
