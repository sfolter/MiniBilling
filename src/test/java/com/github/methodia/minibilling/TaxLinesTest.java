package com.github.methodia.minibilling;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

public class TaxLinesTest {
    @Test
    public void getTaxes(){
        final TaxLines taxes = new TaxLines(1, 0, "Standing charge", 69, "days", new BigDecimal("1.6"), new BigDecimal("56"));

        Assertions.assertEquals(1, taxes.getIndex(), "Index didn't match the expected.");
        Assertions.assertEquals(0, taxes.getLines(), "Lines didn't match the expected.");
        Assertions.assertEquals("Standing charge", taxes.getName(), "Name didn't match the expected.");
        Assertions.assertEquals(69, taxes.getQuantity(), "Quantity didn't match the expected.");
        Assertions.assertEquals("days", taxes.getUnit(), "Unit didn't match the expected.");
        Assertions.assertEquals(new BigDecimal("1.6"), taxes.getPrice(), "Price didn't match the expected.");
        Assertions.assertEquals(new BigDecimal("56"), taxes.getAmount(), "Amount didn't match the expected.");
    }
}
