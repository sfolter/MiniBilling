package com.github.methodia.minibilling;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class QuantityPricePeriodTest {
    @Test
    void qppGetters(){
        final QuantityPricePeriod qpp = new QuantityPricePeriod(LocalDateTime.of(2021, 1, 1, 1, 1, 1),
                LocalDateTime.of(2021, 2, 2, 2, 2, 2), new BigDecimal("2.50"),
                "gas", new BigDecimal("2.2"));

        Assertions.assertEquals(LocalDateTime.of(2021, 1, 1, 1, 1, 1),
                qpp.start(),"Start time getter returns wrong information.");
        Assertions.assertEquals(LocalDateTime.of(2021,2,2,2,2,2),qpp.end(),
        "End time getter returns wrong information");
        Assertions.assertEquals(new BigDecimal("2.50"),qpp.price(),"Qpp price getter returns wrong information");
        Assertions.assertEquals("gas", qpp.product(),"QPP product getter returns wrong information");
        Assertions.assertEquals(new BigDecimal("2.2"),qpp.quantity(),
                "Quantity getter returns wrong information");

    }
}
