package com.github.methodia.minibilling;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface PricesReader {
    List<Price> read(String directory, int priceListNumber);
}
