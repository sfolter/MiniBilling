package com.github.methodia.minibilling.mainlogic;

import com.github.methodia.minibilling.currency.CurrencyCalculator;
import com.github.methodia.minibilling.entity.InvoiceLine;
import com.github.methodia.minibilling.entity.Tax;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class TaxStandingGenerator implements TaxGenerator {

    private final List<InvoiceLine> invoiceLines;
    final CurrencyCalculator currencyCalculator;
    final String fromCurrency;
    final String toCurrency;

    public TaxStandingGenerator(final List<InvoiceLine> invoiceLines, final CurrencyCalculator currencyCalculator,
                                final String fromCurrency,
                                final String toCurrency) {
        this.invoiceLines = invoiceLines;
        this.currencyCalculator = currencyCalculator;
        this.fromCurrency = fromCurrency;
        this.toCurrency = toCurrency;
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
            final BigDecimal value = BigDecimal.valueOf(quantityTax).multiply(BigDecimal.valueOf(1.6));
            final BigDecimal amount = currencyCalculator.calculate(value, fromCurrency, toCurrency)
                    .setScale(2, RoundingMode.HALF_UP).stripTrailingZeros();
            taxes.add(new Tax(taxes.size() + 1, taxedLines, "Standing charge", quantityTax, "days", price, amount));
        }
        return taxes;
    }
}
