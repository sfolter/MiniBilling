package com.github.methodia.minibilling;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class TaxGenerator {

    public Tax generate(InvoiceLine invoiceLine,BigDecimal currencyValue, BigDecimal taxAmount, int taxListSize) {

        List<Integer> invoiceIndex = new ArrayList<>();
        invoiceIndex.add(invoiceLine.getIndex());
        BigDecimal quantity = new BigDecimal(ChronoUnit.DAYS.between(invoiceLine.getStart(), invoiceLine.getEnd()));
        BigDecimal amount = quantity.multiply(taxAmount).multiply(currencyValue).setScale(2, RoundingMode.HALF_UP);
        return new Tax(taxListSize + 1, invoiceIndex, quantity, amount);
    }
}

