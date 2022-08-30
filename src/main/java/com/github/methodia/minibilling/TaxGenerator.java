package com.github.methodia.minibilling;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class TaxGenerator {

    public Tax generate(BigDecimal taxAmount, InvoiceLine invoiceLine, int taxListSize) {

        List<Integer> invoiceIndex = new ArrayList<>();
        invoiceIndex.add(invoiceLine.getIndex());
        BigDecimal quantity = new BigDecimal(ChronoUnit.DAYS.between(invoiceLine.getStart(), invoiceLine.getEnd()));
        BigDecimal amount = quantity.multiply(taxAmount);
        return new Tax(taxListSize + 1, invoiceIndex, quantity, amount);
    }
}

