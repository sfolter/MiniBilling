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
        Assertions.assertEquals("gas",prices.get(0).getProduct(), "Product does not match" );
        Assertions.assertEquals(LocalDate.of(2021, 1,1),prices.get(0).getStart(), "Start date does not match");
        Assertions.assertEquals(LocalDate.of(2022,12,31), prices.get(0).getEnd(), "End date does not match");
        Assertions.assertEquals(new BigDecimal("1.8"), prices.get(0).getValue(), "Value does not match");
    }
}
