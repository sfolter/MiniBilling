package com.github.methodia.minibilling;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author Miroslav Kovachev
 * 28.07.2022
 * Methodia Inc.
 */
public interface PricesReader {

   List<Price> read(int priceListNum,String path) throws IOException;
}
