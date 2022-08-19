package com.github.methodia.minibilling;

import java.math.BigDecimal;

public class Vat {

    private final int index;
    private final int lines;
    private final int taxes;
    private final int taxedAmountPercentage;
    private final int percentage;
    private final BigDecimal amount;

    public Vat(final int index, final int lines, final int taxes, final int taxedAmountPercentage, final int percentage,
               final BigDecimal amount) {
        this.index = index;
        this.lines = lines;
        this.taxes = taxes;
        this.taxedAmountPercentage = taxedAmountPercentage;
        this.percentage = percentage;
        this.amount = amount;
    }

    public int getIndex() {
        return index;
    }

    public int getLines() {
        return lines;
    }

    public int getTaxes() {
        return taxes;
    }

    public int getTaxedAmountPercentage() {
        return taxedAmountPercentage;
    }

    public int getPercentage() {
        return percentage;
    }

    public BigDecimal getAmount() {
        return amount;
    }
}
