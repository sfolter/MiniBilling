package com.github.methodia.minibilling;

import com.github.methodia.minibilling.entityClasses.InvoiceLine;
import com.github.methodia.minibilling.entityClasses.Tax;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class TaxGenerator {

    public Tax generate(final InvoiceLine invoiceLine, final BigDecimal currencyValue, final BigDecimal taxAmount, final int taxListSize) {

        final List<Integer> invoiceIndex = new ArrayList<>();
        invoiceIndex.add(invoiceLine.getIndex());
        final BigDecimal quantity = new BigDecimal(ChronoUnit.DAYS.between(invoiceLine.getStart(), invoiceLine.getEnd()));
        final BigDecimal amount = quantity.multiply(taxAmount).multiply(currencyValue).setScale(2, RoundingMode.HALF_UP);
        return new Tax(taxListSize + 1, invoiceIndex, quantity, amount);
    }
}

