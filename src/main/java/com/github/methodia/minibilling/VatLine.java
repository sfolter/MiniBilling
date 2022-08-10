package com.github.methodia.minibilling;

import java.math.BigDecimal;
import java.util.List;

public class VatLine {
    int index;
    List<Integer> lines;
    int percentage;
    BigDecimal amount;

    public VatLine(int index, List<Integer> lines, int percentage, BigDecimal amount) {
        this.index = index;
        this.lines = lines;
        this.percentage = percentage;
        this.amount = amount;
    }

    public int getIndex() {
        return index;
    }

    public List<Integer> getLines() {
        return lines;
    }

    public int getPercentage() {
        return percentage;
    }

    public BigDecimal getAmount() {
        return amount;
    }
}