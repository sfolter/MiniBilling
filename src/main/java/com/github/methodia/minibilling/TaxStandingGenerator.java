package com.github.methodia.minibilling;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class TaxStandingGenerator implements TaxGenerator {
    private final List<InvoiceLine> invoiceLines;
    public TaxStandingGenerator(final List<InvoiceLine> invoiceLines) {
        this.invoiceLines = invoiceLines;
    }
    @Override
    public List<Tax> generate() {
        final List<Tax> taxes = new ArrayList<>();
        final BigDecimal price = new BigDecimal("1.6");
        for (final InvoiceLine invoiceLine : invoiceLines) {

            final List<Integer> taxedLines = new ArrayList<>();
            //noinspection AutoBoxing
            taxedLines.add(invoiceLine.getIndex());
            final long quantityTax = invoiceLine.getLineStart().until(invoiceLine.getLineEnd(), ChronoUnit.DAYS) + 1;
            final BigDecimal amount = BigDecimal.valueOf(quantityTax).multiply(BigDecimal.valueOf(1.6));
            taxes.add(new Tax(taxes.size() + 1, taxedLines, "Standing charge", quantityTax, "days", price, amount));
        }
        return taxes;
    }
}
