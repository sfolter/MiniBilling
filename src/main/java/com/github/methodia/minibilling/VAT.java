package com.github.methodia.minibilling;

import java.math.BigDecimal;
import java.util.List;

public class VAT {
    private int index;
    private int lines;
    private int percentage;
    private BigDecimal amount;

    public VAT(int index, int lines, int percentage, BigDecimal amount) {
        this.index = index;
        this.lines = lines;
        this.percentage = percentage;
        this.amount = amount;
    }

    public int getIndex() {
        return index;
    }

    public int getLines() {
        return lines;
    }

    public int getPercentage() {
        return percentage;
    }

    public BigDecimal getAmount() {
        return amount;
    }
}
