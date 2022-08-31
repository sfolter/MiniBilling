package com.github.methodia.minibilling;

import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.chrono.ChronoLocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class InvoiceTest {
    @Test
    void testSample1() {
        /*
        TaxLines(int index, int lines, String name, int quantity, String unit, BigDecimal price, BigDecimal amount)
         */

        List<Price> priceList = new ArrayList<>();
        priceList.add(new Price("gas", LocalDate.of(2022, 8, 29), LocalDate.of(2022, 8, 31), new BigDecimal("10")));

        User user = new User("Ivan Georgiev Ivanov", "1", 1, priceList);

        List<InvoiceLine> invoiceLines = new ArrayList<>();
        invoiceLines.add(new InvoiceLine(1, new BigDecimal("10"),(LocalDateTime.of(LocalDate.of(2022,8,30), LocalTime.of(12,59,13))),
                LocalDateTime.of(LocalDate.of(2022,8,30), LocalTime.of(12,59,13)), "gas", new BigDecimal("10"),
                1, new BigDecimal("10")));

        List<VatLine> vatLines = new ArrayList<>();
        vatLines.add(new VatLine(1, 1, 10, 20, 10, new BigDecimal("12.5")));

        List<TaxLines> taxLines = new ArrayList<>();
        taxLines.add(new TaxLines(1,1,"Standing Charge",10,"days", new BigDecimal("12"), new BigDecimal("100")));

        Invoice invoice = new Invoice(LocalDateTime.of(LocalDate.of(2022,8,30), LocalTime.of(12,59,13)), "1",
                user, new BigDecimal("10"), new BigDecimal("100"), invoiceLines,vatLines,taxLines);

        Assertions.assertEquals("1", invoice.getDocumentNumberTest(), "Document number is incorrect");
        Assertions.assertEquals(new BigDecimal("10"), invoice.getTotalAmount(), "Total amount is incorrect");
        Assertions.assertEquals(new BigDecimal("100"), invoice.getTotalAmountWithVat(), "Total amount with VAT is incorrect");

    }
}
