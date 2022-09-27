package com.github.methodia.minibilling;

import com.github.methodia.minibilling.entityClasses.InvoiceLine;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class InvoiceLineTest {
@Test
    void invoiceLineGetters(){
    final InvoiceLine invoiceLine = new InvoiceLine(1, new BigDecimal("100"),
            LocalDateTime.of(2021, 1, 1, 1, 1, 2),
            LocalDateTime.of(2021, 2, 2, 2, 2, 2),
            "gas", new BigDecimal("2.50"), 1, new BigDecimal("100"));

    Assertions.assertEquals(1,invoiceLine.getIndex(),
            "InvoiceLine getIndex method returns incorrect information.");
    Assertions.assertEquals(new BigDecimal("100"),invoiceLine.getQuantity(),
            "InvoiceLine getQuantity method returns incorrect information.");
    Assertions.assertEquals(LocalDateTime.of(2021,1,1,1,1,2),invoiceLine.getStart(),
    "InvoiceLine getStart method returns incorrect information.");
    Assertions.assertEquals(LocalDateTime.of(2021,2,2,2,2,2),invoiceLine.getEnd(),
            "InvoiceLine getEnd method returns incorrect information.");
    Assertions.assertEquals("gas",invoiceLine.getProduct(),
            "InvoiceLine getProduct method returns incorrect information");
    Assertions.assertEquals(new BigDecimal("2.50"),invoiceLine.getPrice(),
    "InvoiceLine getPrice method returns incorrect information.");
    Assertions.assertEquals(1,invoiceLine.getPriceList(),
            "InvoiceLine getPriceList method returns incorrect information.");
    Assertions.assertEquals(new BigDecimal("100"),invoiceLine.getAmount(),
            "InvoiceLine getAmount method returns incorrect information.");
}
}
