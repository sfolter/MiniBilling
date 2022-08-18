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

    public List<Tax> createTaxes(List<InvoiceLine> invoiceLines) {
        List<Tax> taxes = new ArrayList<>();
        for (InvoiceLine invoiceLine : invoiceLines) {

            List<Integer> taxedLines = new ArrayList<>();
            taxedLines.add(invoiceLine.getIndex());
            long quantityTax = invoiceLine.getLineStart().until(invoiceLine.getLineEnd(), ChronoUnit.DAYS) + 1;
            BigDecimal amount = BigDecimal.valueOf(quantityTax).multiply(BigDecimal.valueOf(1.6));
            totalAmountTaxes = totalAmountTaxes.add(amount);
            taxes.add(new Tax(taxes.size() + 1, taxedLines, quantityTax, amount));
        }
        return taxes;
    }
}
