package com.github.methodia.minibilling;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.math.BigDecimal;

public class TaxesTest {

    @Test
    public void getTaxes() {
        final Taxes taxes = new Taxes(1, 0, "Standing charge", 69, "days", new BigDecimal("1.6"), new BigDecimal("56"));

        Assertions.assertEquals(1, taxes.getIndex(), "Index does not match the expected.");
        Assertions.assertEquals(0, taxes.getLines(), "Lines does not match the expected.");
        Assertions.assertEquals("Standing charge", taxes.getName(), "Name does not match the expected.");
        Assertions.assertEquals(69, taxes.getQuantity(), "Quantity does not match the expected.");
        Assertions.assertEquals("days", taxes.getUnit(), "Unit does not match the expected.");
        Assertions.assertEquals(new BigDecimal("1.6"), taxes.getPrice(), "Price does not match the expected.");
        Assertions.assertEquals(new BigDecimal("56"), taxes.getAmount(), "Amount does not match the expected.");
    }
}
