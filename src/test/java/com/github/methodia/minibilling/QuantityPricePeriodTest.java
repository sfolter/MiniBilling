package com.github.methodia.minibilling;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class QuantityPricePeriodTest {

    @Test
    public void getQPP() {
        final Price price = new Price("gas", LocalDate.of(2022, 2, 2), LocalDate.of(2022, 3, 3), new BigDecimal("1.5"));
        final QuantityPricePeriod qpp = new QuantityPricePeriod(LocalDateTime.of(2021, 5, 26, 12, 12, 12, 12),
                LocalDateTime.of(2022, 3, 3, 3, 3, 3, 3), price, new BigDecimal("60"));

        Assertions.assertEquals(LocalDateTime.of(2021, 5, 26, 12, 12, 12, 12), qpp.getStart(),
                "Start date did not match.");
        Assertions.assertEquals(LocalDateTime.of(2022, 3, 3, 3, 3, 3, 3), qpp.getEnd(), "End date did not match.");
        Assertions.assertEquals(price, qpp.getPrice(), "Price did not match.");
        Assertions.assertEquals(new BigDecimal("60"), qpp.getQuantity(), "Quantity did not match.");
    }
}
