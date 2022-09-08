package com.github.methodia.minibilling;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.math.BigDecimal;
import java.time.LocalDate;

public class PriceTest {

    @Test
    public void Sample1() {
        final Price price = new Price("gas", LocalDate.of(2022, 3, 4), LocalDate.of(2022, 3, 3), new BigDecimal("1.5"));

        Assertions.assertEquals("gas", price.getProduct(),
                "Product doesn't match.");
        Assertions.assertEquals(LocalDate.of(2022, 3, 4), price.getStart(),
                "Start date doesn't match.");
        Assertions.assertEquals(LocalDate.of(2022, 3, 3), price.getEnd(),
                "End date doesn't match.");
        Assertions.assertEquals(new BigDecimal("1.5"), price.getValue(),
                "Value doesn't match.");
    }
}