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
                LocalDateTime.of(2022, 3, 3, 3, 3, 3, 3), price.getValue() , price.getProduct(), new BigDecimal("60"));

        Assertions.assertEquals(LocalDateTime.of(2021, 5, 26, 12, 12, 12, 12), qpp.getStart(),
                "Start date does not match.");
        Assertions.assertEquals(LocalDateTime.of(2022, 3, 3, 3, 3, 3, 3), qpp.getEnd(), "End date does not match.");
        Assertions.assertEquals(new BigDecimal("1.5"), qpp.getPrice(), "Price does not match.");
        Assertions.assertEquals(new BigDecimal("60"), qpp.getQuantity(), "Quantity does not match.");
        Assertions.assertEquals("gas", qpp.getProduct(), "Product does not match");
    }
}
