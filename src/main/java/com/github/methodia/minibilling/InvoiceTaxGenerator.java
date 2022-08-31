package com.github.methodia.minibilling;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class InvoiceTaxGenerator implements TaxGenerator {

    public Tax generateTaxes(InvoiceLine invoiceLine) {
        List<Integer> linesOfTax = new ArrayList<>();
        linesOfTax.add(invoiceLine.getIndex());

        BigDecimal quantity = BigDecimal.valueOf(
                invoiceLine.getLineStart().until(invoiceLine.getLineEnd(), ChronoUnit.DAYS) + 1);
        BigDecimal taxAmount = quantity.multiply(new BigDecimal("1.6"));

        return new Tax(invoiceLine.getIndex(), linesOfTax, quantity, new BigDecimal("1.6"), taxAmount);
    }
}
