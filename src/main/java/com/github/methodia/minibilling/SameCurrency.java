package com.github.methodia.minibilling;

import java.math.BigDecimal;

public class SameCurrency implements CurrencyCalculator{

    @Override
    public BigDecimal calculate(BigDecimal amount, String fromCurrency, String toCurrency) {
        return amount;
    }
}
