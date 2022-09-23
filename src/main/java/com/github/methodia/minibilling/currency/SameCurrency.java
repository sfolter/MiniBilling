package com.github.methodia.minibilling.currency;

import com.github.methodia.minibilling.currency.CurrencyCalculator;

import java.math.BigDecimal;

public class SameCurrency implements CurrencyCalculator {

    @Override
    public BigDecimal calculate(final BigDecimal amount, final String fromCurrency, final String toCurrency) {
        return amount;
    }
}
