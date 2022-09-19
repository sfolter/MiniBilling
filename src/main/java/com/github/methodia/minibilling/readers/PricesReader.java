package com.github.methodia.minibilling.readers;

import com.github.methodia.minibilling.entity.Price;

import java.util.List;

public interface PricesReader {

    List<Price> read();
}
