package com.example.SpringBatchExample.generators;

import com.example.SpringBatchExample.models.InvoiceLine;
import com.example.SpringBatchExample.models.Tax;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;


public class InvoiceTaxGenerator implements TaxGenerator {

    @Override
    public Tax generateTaxes(final InvoiceLine invoiceLine) {

        final List<Integer> linesOfTax = new ArrayList<>();
        linesOfTax.add(invoiceLine.getIndex());

                /*CurrencyConverter currencyConverter = new CurrencyConverter();
                BigDecimal currencyRate = currencyConverter.convertTo(currencyFrom,currencyTo,amount);*/

        final BigDecimal quantity = BigDecimal.valueOf(
                invoiceLine.getLineStart().until(invoiceLine.getLineEnd(), ChronoUnit.DAYS) + 1);
        final BigDecimal currencyRate = new BigDecimal("0.5117");

        final BigDecimal taxAmount = quantity.multiply(new BigDecimal("1.6")).multiply(currencyRate);

        return new Tax(invoiceLine.getIndex(), linesOfTax, quantity, new BigDecimal("1.6"), taxAmount);
    }
}
