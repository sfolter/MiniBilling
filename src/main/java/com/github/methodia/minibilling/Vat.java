package com.github.methodia.minibilling;

import java.math.BigDecimal;
import java.util.List;

public class Vat {

    private final int index;
    private final List<Integer> lines;
    private final List<Integer> taxes;
    private final BigDecimal taxedAmountPercentage;
    private final BigDecimal percentage;
    private final BigDecimal amount;

    public Vat(final int index, final List<Integer> lines, final List<Integer> taxes, final BigDecimal taxedAmountPercentage,
               final BigDecimal percentage, final BigDecimal amount) {
        this.index = index;
        this.lines = lines;
        this.taxes = taxes;
        this.taxedAmountPercentage = taxedAmountPercentage;
        this.percentage = percentage;
        this.amount = amount;
    }


    public List<Integer> getLines() {
        return lines;
    }

    public BigDecimal getAmount() {
        return amount;
    }
}
