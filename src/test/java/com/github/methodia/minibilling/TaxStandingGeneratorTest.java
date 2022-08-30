package com.github.methodia.minibilling;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;


class TaxStandingGeneratorTest {

    @Test
    void calculationDays() {
        final InvoiceLine invoiceLine = new InvoiceLine(1, new BigDecimal("100"),
                LocalDateTime.of(2021, Month.APRIL, 3, 20, 20, 20),
                LocalDateTime.of(2021, Month.MAY, 3, 20, 20, 20),
                "gas", new BigDecimal("1.6"), 1, new BigDecimal("160"));
        final List<InvoiceLine> invoiceLines = new ArrayList<>();
        invoiceLines.add(invoiceLine);
        final TaxStandingGenerator taxStandingGenerator = new TaxStandingGenerator(invoiceLines);
        final List<Tax> taxes = taxStandingGenerator.generate();
        Assertions.assertEquals(1, taxes.get(0).getLineIndex().get(0),
                "Index for line is not the same.");
        Assertions.assertEquals(31, taxes.get(0).getQuantity(),
                "Days of tax doesn't match with line period.");
        Assertions.assertEquals(new BigDecimal("49.6"), taxes.get(0).getAmount());
    }
}