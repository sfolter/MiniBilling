package com.github.methodia.minibilling;

import java.math.BigDecimal;

public record VatPercentages(BigDecimal taxedAmountPercentage, BigDecimal percentage) {

}