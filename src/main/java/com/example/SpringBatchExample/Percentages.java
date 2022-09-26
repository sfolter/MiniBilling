package com.example.SpringBatchExample;

import java.math.BigDecimal;

public class Percentages {

    private final BigDecimal taxedAmountPercentage;
    private final BigDecimal percentage;

    public Percentages(final BigDecimal taxedAmountPercentage, final BigDecimal percentage) {
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
