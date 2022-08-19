package com.github.methodia.minibilling;

import java.math.BigDecimal;
import java.util.List;

public class Vat {

    private final int index;
    private final List<Integer> lines;
    private final BigDecimal percentage;
    private final BigDecimal amount;

    public Vat(int index, List<Integer> lines,String percentage, BigDecimal amount) {
        this.index = index;
        this.lines = lines;
        this.percentage = new BigDecimal(String.valueOf(percentage)).divide(new BigDecimal("100"));
        this.amount = amount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public List<Integer> getLines() {
        return lines;
    }


}
