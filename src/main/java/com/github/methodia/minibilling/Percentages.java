package com.github.methodia.minibilling;

import java.math.BigDecimal;

public class Percentages {
    private final BigDecimal taxedAmountPercentage;
    private final BigDecimal percentage;

    public Percentages(BigDecimal taxedAmountPercentage, BigDecimal percentage) {
        this.taxedAmountPercentage = taxedAmountPercentage;
        this.percentage = percentage;
    }

    public BigDecimal getTaxedAmountPercentage() {
        return taxedAmountPercentage;
    }

    public BigDecimal getPercentage() {
        return percentage;
    }
}
