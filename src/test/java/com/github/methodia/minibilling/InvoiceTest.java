package com.github.methodia.minibilling;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class InvoiceTest {

    @Test
    public void getInvoice() {
        final List<Price> priceList = new LinkedList<>();
        priceList.add(new Price("gas", LocalDate.of(2022, 5, 7), LocalDate.of(2022, 6, 8), new BigDecimal("1.5")));
        final User user = new User("Ivan", "1", 1);
        final List<InvoiceLine> lines = new ArrayList<>();
        lines.add(new InvoiceLine(1, new BigDecimal("436"),
                LocalDateTime.of(2021, 1, 1, 14, 40, 0),
                LocalDateTime.of(2021, 3, 11, 7, 0, 0), "gas", new BigDecimal("1.8"), 1, new BigDecimal("401")));
        final List<Vat> vats = new ArrayList<>();
        vats.add(new Vat(1, 1, 0, 60, 20, new BigDecimal("150")));
        final List<Taxes> taxesLines = new ArrayList<>();
        taxesLines.add(new Taxes(1, 0, "Standing charge", 69, "days", new BigDecimal("1.6"), new BigDecimal("56")));
        final Invoice invoice = new Invoice(LocalDateTime.of(2022, 8, 30, 10, 53, 32), "10001", user,
                new BigDecimal("511"),
                new BigDecimal("1014"), lines, vats, taxesLines);

        Assertions.assertEquals(LocalDateTime.of(2022, 8, 30, 10, 53, 32), invoice.getDocumentDate(),
                "Document date does not match");
        Assertions.assertEquals(user, invoice.getConsumer(), "User does not match");
        Assertions.assertEquals(new BigDecimal("511"), invoice.getTotalAmount(), "Total amount does not match");
        Assertions.assertEquals(new BigDecimal("1014"), invoice.getTotalAmountWithVat(),
                "Total amount with vat does not match");
        Assertions.assertEquals(lines, invoice.getLines(), "Lines does not match");
        Assertions.assertEquals(vats, invoice.getVatsLines(), "Vats does not match");
        Assertions.assertEquals(taxesLines, invoice.getTaxesLines(), "Taxes does not match");
    }
}
