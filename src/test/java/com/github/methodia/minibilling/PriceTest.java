package com.github.methodia.minibilling;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

public class PriceTest {
    @Test
    void testPriceGetters(){
        Price price = new Price("gas", LocalDate.of(2022, 04, 05)
                , LocalDate.of(2022, 05, 05), new BigDecimal("2.50"));

        Assertions.assertEquals("gas",price.getProduct(),"Price product getter is incorrect");
        Assertions.assertEquals(LocalDate.of(2022,4,5),price.getStart(),"Price startDate getter is incorrect");
        Assertions.assertEquals(LocalDate.of(2022,5,5),price.getEnd(),"Price endDate getter is incorrect");
        Assertions.assertEquals(new BigDecimal("2.50"),price.getValue(),"Price value getter is incorrect");
    }
}
