package com.github.methodia.minibilling;

import java.math.BigDecimal;

public class VatPercentage {

    private final BigDecimal taxedAmountPercentage;
    private final BigDecimal percentage;

    public VatPercentage(final BigDecimal taxedAmountPercentage, final BigDecimal percentage) {
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