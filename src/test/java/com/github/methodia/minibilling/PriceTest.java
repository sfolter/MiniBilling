package com.github.methodia.minibilling;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.math.BigDecimal;
import java.time.LocalDate;

public class PriceTest {
    @Test
    public void getPrice(){
        final Price price = new Price("gas", LocalDate.of(2022, 2, 2), LocalDate.of(2022, 3, 3), new BigDecimal("1.5"));

        Assertions.assertEquals("gas", price.getProduct(), "Product did not match the expected one.");
        Assertions.assertEquals(LocalDate.of(2022,2,2), price.getStart(), "Start date did not match the expected one.");
        Assertions.assertEquals(LocalDate.of(2022,3,3), price.getEnd(), "End date did not match the expected one.");
        Assertions.assertEquals(new BigDecimal("1.5"), price.getValue(), "Value did not match the expected one.");
    }
}
