package com.github.methodia.minibilling;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class TaxGenerator {

    private BigDecimal totalAmountTaxes = BigDecimal.ZERO;

    public BigDecimal getTotalAmountTaxes() {
        return totalAmountTaxes;
    }

    public List<Tax> createTaxes(final List<InvoiceLine> invoiceLines) {
        final List<Tax> taxes = new ArrayList<>();
        for (final InvoiceLine invoiceLine : invoiceLines) {

            final List<Integer> taxedLines = new ArrayList<>();
            //noinspection AutoBoxing
            taxedLines.add(invoiceLine.getIndex());
            final long quantityTax = invoiceLine.getLineStart().until(invoiceLine.getLineEnd(), ChronoUnit.DAYS) + 1;
            final BigDecimal amount = BigDecimal.valueOf(quantityTax).multiply(BigDecimal.valueOf(1.6));
            totalAmountTaxes = totalAmountTaxes.add(amount);
            taxes.add(new Tax(taxes.size() + 1, taxedLines, quantityTax, amount));
        }
        return taxes;
    }
}
