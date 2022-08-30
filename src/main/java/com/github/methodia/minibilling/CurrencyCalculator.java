package com.github.methodia.minibilling;

import java.math.BigDecimal;
import java.util.function.BiConsumer;

public interface CurrencyCalculator {
    BigDecimal calculate(String fromCurrency,String toCurrency,BigDecimal amount);
}
