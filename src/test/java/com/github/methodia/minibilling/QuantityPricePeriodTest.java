package com.github.methodia.minibilling;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class QuantityPricePeriodTest {
    @Test
    void qppGetters(){
        QuantityPricePeriod qpp = new QuantityPricePeriod(LocalDateTime.of(2021, 1, 1, 1, 1, 1),
                LocalDateTime.of(2021, 2, 2, 2, 2, 2), new BigDecimal("2.50"),
                "gas", new BigDecimal("2.2"));

        Assertions.assertEquals(LocalDateTime.of(2021, 1, 1, 1, 1, 1),
                qpp.getStart(),"Start time getter returns wrong information.");
        Assertions.assertEquals(LocalDateTime.of(2021,2,2,2,2,2),qpp.getEnd(),
        "End time getter returns wrong information");
        Assertions.assertEquals(new BigDecimal("2.50"),qpp.getPrice(),"Qpp price getter returns wrong information");
        Assertions.assertEquals("gas", qpp.getProduct(),"QPP product getter returns wrong information");
        Assertions.assertEquals(new BigDecimal("2.2"),qpp.getQuantity(),
                "Quantity getter returns wrong information");

    }
}
