package com.github.methodia.minibilling;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class InvoiceTaxGenerator implements TaxGenerator {

    public Tax generateTaxes(InvoiceLine invoiceLine) {

        List<Integer> linesOfTax = new ArrayList<>();
        linesOfTax.add(invoiceLine.getIndex());

        //        CurrencyConverter currencyConverter = new CurrencyConverter();
        //        BigDecimal currencyRate = currencyConverter.convertTo(currencyFrom,currencyTo,amount);
        BigDecimal currencyRate = new BigDecimal("0.5117");

        BigDecimal quantity = BigDecimal.valueOf(
                invoiceLine.getLineStart().until(invoiceLine.getLineEnd(), ChronoUnit.DAYS) + 1);
        BigDecimal taxAmount = quantity.multiply(new BigDecimal("1.6")).multiply(currencyRate);

        return new Tax(invoiceLine.getIndex(), linesOfTax, quantity, new BigDecimal("1.6"), taxAmount);
    }
}
