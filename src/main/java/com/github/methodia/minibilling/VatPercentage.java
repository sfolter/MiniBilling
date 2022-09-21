package com.github.methodia.minibilling;

import java.math.BigDecimal;

public class VatPercentage {

    int taxedAmountPercentage;
    int percentage;

    public VatPercentage(final int taxedAmountPercentage, final int percentage) {
        this.taxedAmountPercentage = taxedAmountPercentage;
        this.percentage = percentage;
    }

    public int getTaxedAmountPercentage() {
        return taxedAmountPercentage;
    }

    public int getPercentage() {
        return percentage;
    }
}
