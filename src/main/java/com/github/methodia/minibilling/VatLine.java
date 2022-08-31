package com.github.methodia.minibilling;

import java.math.BigDecimal;

public class VatLine {
    private int index;
    private int lines;
    private int percentage;
    private BigDecimal amount;
    private final int taxedAmountPercentage;
    private final int taxes;


    public VatLine(int index, int lines, int taxes, int taxedAmountPercentage, int percentage, BigDecimal amount) {
        this.index = index;
        this.lines = lines;
        this.percentage = percentage;
        this.amount = amount;
        this.taxedAmountPercentage = taxedAmountPercentage;
        this.taxes = taxes;
    }

    public int getIndex() {
        return index;
    }

    public int getLines() {
        return lines;
    }

    public int getTaxes(){return taxes;}

    public int getTaxedAmountPercentage(){return taxedAmountPercentage;}

    public int getPercentage() {
        return percentage;
    }

    public BigDecimal getAmount() {
        return amount;
    }
}
