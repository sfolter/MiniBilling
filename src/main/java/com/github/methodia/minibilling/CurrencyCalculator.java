package com.github.methodia.minibilling;

import java.math.BigDecimal;

public interface CurrencyCalculator {

    BigDecimal calculate(BigDecimal amount, String fromCurrency, String toCurrency);
}
