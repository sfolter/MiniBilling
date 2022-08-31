package com.github.methodia.minibilling;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class InvoiceTest {

    @Test
    public void getInvoice() {
        final ArrayList<Price> priceList = new ArrayList<>();
        priceList.add(new Price("gas", LocalDate.of(2022, 5, 7), LocalDate.of(2022, 6, 8), new BigDecimal("1.5")));
        final User user = new User("Ivan", "1", 1, priceList);
        final List<InvoiceLine> lines = new ArrayList<>();
        lines.add(new InvoiceLine(1, new BigDecimal("436"),
                LocalDateTime.of(2021, 1, 1, 14, 40, 00),
                LocalDateTime.of(2021, 03, 11, 7, 00, 00), "gas", new BigDecimal("1.8"), 1, new BigDecimal("401")));
        final List<Vat> vats = new ArrayList<>();
        vats.add(new Vat(1, 1, 0, 60, 20, new BigDecimal("150")));
        final List<Taxes> taxesLines = new ArrayList<>();
        taxesLines.add(new Taxes(1, 0, "Standing charge", 69, "days", new BigDecimal("1.6"), new BigDecimal("56")));
        final Invoice invoice = new Invoice(LocalDateTime.of(2022, 8, 30, 10, 53, 32), "10001", user,
                new BigDecimal("511"),
                new BigDecimal("1014"), lines, vats, taxesLines);

        Assertions.assertEquals(LocalDateTime.of(2022, 8, 30, 10, 53, 32), invoice.getDocumentDate(),
                "Document date did not match");
        Assertions.assertEquals(user, invoice.getConsumer(), "User did not match");
        Assertions.assertEquals(new BigDecimal("511"), invoice.getTotalAmount(), "Total amount did not match");
        Assertions.assertEquals(new BigDecimal("1014"), invoice.getTotalAmountWithVat(),
                "Total amount with vat did not match");
        Assertions.assertEquals(lines, invoice.getLines(), "Lines did not match");
        Assertions.assertEquals(vats, invoice.getVatsLines(), "Vats did not match");
        Assertions.assertEquals(taxesLines, invoice.getTaxesLines(), "Taxes did not match");
    }
}
