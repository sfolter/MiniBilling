package com.github.methodia.minibilling;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class InvoiceLineTest {

    @Test
    public void getInvoiceLine9() {
        final InvoiceLine invoiceLine = new InvoiceLine(1, new BigDecimal("436"),
                LocalDateTime.of(2021, 1, 1, 14, 40, 00),
                LocalDateTime.of(2021, 03, 11, 7, 00, 00), "gas", new BigDecimal("1.8"), 1, new BigDecimal("401"));

        Assertions.assertEquals(1, invoiceLine.getIndex(), "Index did not match.");
        Assertions.assertEquals(new BigDecimal("436"), invoiceLine.getQuantity(), "Quantity did not match");
        Assertions.assertEquals(LocalDateTime.of(2021, 1, 1, 14, 40, 00), invoiceLine.getStart(),
                "Start date did not match");
        Assertions.assertEquals(LocalDateTime.of(2021, 03, 11, 7, 00, 00), invoiceLine.getEnd(),
                "End date did not match.");
        Assertions.assertEquals("gas", invoiceLine.getProduct(), "Product did not match");
        Assertions.assertEquals(new BigDecimal("1.8"), invoiceLine.getPrice(), "Price did not match.");
        Assertions.assertEquals(new BigDecimal("401"), invoiceLine.getAmount(), "Amount did not match");
    }
}
