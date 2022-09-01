package com.github.methodia.minibilling;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CsvPriceReadingTest {

    @Test
    public void getPrice() {
        final String path = "C:\\java projects\\MiniBilling\\MiniBilling\\out\\test\\resources\\sample1\\input\\";
        final int priceListNum = 1;
        final CsvPricesReader csvPricesReader = new CsvPricesReader(path, priceListNum);
        final List<Price> prices = csvPricesReader.read();
        final List<Price> pricesToCheck = new ArrayList<>();
        pricesToCheck.add(new Price("gas", LocalDate.of(2022, 2, 2), LocalDate.of(2022, 3, 3), new BigDecimal("1.5")));

        Assertions.assertEquals(pricesToCheck.size(), prices.size(), "Prices size does not match");
    }
}
