package com.github.methodia.minibilling;


import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;



public class PricesTest {
    //Test the constructor
    //1. Constructor create Price instance;
    private Prices prices;
    String product = "gas";
    LocalDate startingDate = LocalDate.parse("2021-01-01");
    LocalDate endDate = LocalDate.parse("2022-12-31");
    double price = 2.14;

    @Test
    public void testConstructorShouldCreateValidPrices() {
        prices = new Prices(product, startingDate, endDate, price);

        Assert.assertEquals("gas", prices.getProduct());
        Assert.assertEquals(LocalDate.of(2021, 01, 01), prices.getStartingDate());
        Assert.assertEquals(LocalDate.of(2022, 12, 31), prices.getEndDate());
        Assertions.assertEquals(2.14, prices.getPrice());


    }



}
