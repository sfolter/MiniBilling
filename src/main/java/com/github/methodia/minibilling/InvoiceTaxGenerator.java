package com.github.methodia.minibilling;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class InvoiceTaxGenerator implements TaxGenerator {
    public Tax generateTaxes(InvoiceLine invoiceLine) {
        List<Integer> linesOFTax = new ArrayList<>();
        linesOFTax.add(invoiceLine.getIndex());

        BigDecimal quantity = BigDecimal.valueOf(invoiceLine.getLineStart().until(invoiceLine.getLineEnd(), ChronoUnit.DAYS) + 1);
        BigDecimal taxAmount = quantity.multiply(BigDecimal.valueOf(1.6));

        return new Tax(invoiceLine.getIndex(), linesOFTax, "", quantity, "", BigDecimal.valueOf(1.6), taxAmount);
    }
}
