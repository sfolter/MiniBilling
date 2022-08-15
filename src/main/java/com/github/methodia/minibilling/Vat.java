package com.github.methodia.minibilling;

import java.math.BigDecimal;
import java.util.List;

public class Vat {
    private final int index;
    private final List<Integer> lines;
    private final int percentage;
    private final BigDecimal amount;

    public Vat(int index, List<Integer> lines, int percentage, BigDecimal amount) {
        this.index = index;
        this.lines = lines;
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
