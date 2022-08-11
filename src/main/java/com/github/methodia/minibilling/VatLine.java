package com.github.methodia.minibilling;

import java.math.BigDecimal;

public class VatLine {
    private final int index;
    private final int lines;
    private final BigDecimal percentage;
    private final BigDecimal amount;

    public VatLine(int index, int lineIndex, BigDecimal percentage, BigDecimal amount) {
        this.index = index;
        this.lines = lineIndex;
        this.percentage = percentage;
        this.amount = amount;
    }
    public int getIndex() {
        return index;
    }

    public Integer getLines() {
        return lines;
    }

    public BigDecimal getPercentage() {
        return percentage;
    }

    public BigDecimal getAmount() {
        return amount;
    }
}