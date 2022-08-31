package com.github.methodia.minibilling;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class VatTest {
    @Test
    void testSample1(){

        int expectedIndex = 1;
        int expectedLines = 1;
        int expectedTaxes = 10;
        int expectedTaxedAmountPercentage = 20;
        int expectedPercentage = 10;
        BigDecimal expectedAmount = new BigDecimal("12.5");

        VatLine vat = new VatLine(1, 1, 10, 20, 10, new BigDecimal("12.5"));

        Assertions.assertEquals(expectedIndex,vat.getIndex(),"Index is incorrect.");
        Assertions.assertEquals(expectedLines,vat.getLines(),"Number of lines is incorrect.");
        Assertions.assertEquals(expectedTaxes,vat.getTaxes(),"Tax is incorrect.");
        Assertions.assertEquals(expectedTaxedAmountPercentage,vat.getTaxedAmountPercentage(),"TaxedAmountPercentage is incorrect.");
        Assertions.assertEquals(expectedPercentage,vat.getPercentage(),"Percentage is incorrect.");
        Assertions.assertEquals(expectedAmount ,vat.getAmount(),"Vat amount is incorrect.");



    }
}
