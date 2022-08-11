package com.github.methodia.minibilling;

public class VatPercentage {
    private  final int taxedAmountPercentage;
    private final int percentage;

    public VatPercentage(int taxedAmountPercentage, int percentage) {
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
