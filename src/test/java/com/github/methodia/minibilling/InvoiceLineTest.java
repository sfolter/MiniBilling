package com.github.methodia.minibilling;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class InvoiceLineTest {

    @Test
    public void getInvoiceLine() {
        final InvoiceLine invoiceLine = new InvoiceLine(1, new BigDecimal("436"),
                LocalDateTime.of(2021, 1, 1, 14, 40, 0),
                LocalDateTime.of(2021, 3, 11, 7, 0, 0), "gas", new BigDecimal("1.8"), 1, new BigDecimal("401"));

        Assertions.assertEquals(1, invoiceLine.getIndex(), "Index does not match.");
        Assertions.assertEquals(new BigDecimal("436"), invoiceLine.getQuantity(), "Quantity does not match");
        Assertions.assertEquals(LocalDateTime.of(2021, 1, 1, 14, 40, 0), invoiceLine.getStart(),
                "Start date does not match");
        Assertions.assertEquals(LocalDateTime.of(2021, 3, 11, 7, 0, 0), invoiceLine.getEnd(),
                "End date does not match.");
        Assertions.assertEquals("gas", invoiceLine.getProduct(), "Product does not match");
        Assertions.assertEquals(new BigDecimal("1.8"), invoiceLine.getPrice(), "Price does not match.");
        Assertions.assertEquals(new BigDecimal("401"), invoiceLine.getAmount(), "Amount does not match");
        Assertions.assertEquals(new BigDecimal("436"), invoiceLine.getQuantity(), "Quantity does not match");
        Assertions.assertEquals(1, invoiceLine.getIndex(), "Index does not match");
        Assertions.assertEquals(1, invoiceLine.getPriceList(), "Price list does not match");
        Assertions.assertEquals(LocalDateTime.of(2021, 1, 1, 14, 40), invoiceLine.getStart(),
                "Start date does not match");
        Assertions.assertEquals(LocalDateTime.of(2021, 3, 11, 7, 0), invoiceLine.getEnd(), "End date does not match");
    }
}
