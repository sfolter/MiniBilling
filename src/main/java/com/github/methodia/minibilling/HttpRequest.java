package com.github.methodia.minibilling;

import java.math.BigDecimal;

public interface HttpRequest {


    BigDecimal getCurrencyValue(String currency);
}
