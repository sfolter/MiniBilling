package com.github.methodia.minibilling;

import java.math.BigDecimal;
import java.util.List;

public class Vat {
    private final int index;
    private final List<Integer> lines;
    //private  final int taxedAmountPercentage;
    private final int percentage=20;
    private final BigDecimal amount;

    public Vat(int index, List<Integer> lines, BigDecimal amount) {
        this.index = index;
        this.lines = lines;
        //this.taxedAmountPercentage = taxedAmountPercentage;
        //this.percentage = percentage;
        this.amount = amount;
    }

    public List<Integer> getLines() {
        return lines;
    }

}
