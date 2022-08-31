package com.github.methodia.minibilling;

import java.math.BigDecimal;

public class SameCurrency implements CurrencyCalculator {

    @Override
    public BigDecimal calculate(final BigDecimal amount, final String fromCurrency, final String toCurrency) {
        return amount;
    }
}
