package com.github.methodia.minibilling;

import java.math.BigDecimal;

public interface CurrencyConverter {


    BigDecimal getCurrencyValue(String currency);
}
