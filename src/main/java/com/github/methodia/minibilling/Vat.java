package com.github.methodia.minibilling;

import java.math.BigDecimal;
import java.util.List;

public class Vat {
    private final int index;
    private final List<Integer> lines;
    private final int percentage;
    private final BigDecimal amount;

    public Vat(int index, List<Integer> lines, BigDecimal amount) {
        this.index = index;
        this.lines = lines;
        this.percentage = 20;
        this.amount = amount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public List<Integer> getLines() {
        return lines;
    }


}
