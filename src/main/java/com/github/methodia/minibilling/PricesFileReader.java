package com.github.methodia.minibilling;

import java.util.List;

public interface PricesFileReader {
    List<Price> read(String directory, int priceListNumber);
}
