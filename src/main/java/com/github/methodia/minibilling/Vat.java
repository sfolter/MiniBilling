package com.github.methodia.minibilling;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Vat {

    private final int index;
    private final List<Integer> lines;
    private final List<Integer> taxes;

    private final BigDecimal taxedAmountPercentage;
    private final BigDecimal percentage;
    private final BigDecimal amount;



    public Vat(int index, List<Integer> lines, BigDecimal taxedAmountPercentage, String percentage, BigDecimal amount) {
        this.index = index;
        this.lines = lines;
        this.taxes = new ArrayList<>();
        this.taxedAmountPercentage = taxedAmountPercentage;
        this.percentage = new BigDecimal(String.valueOf(percentage));
        this.amount = amount;

    }

    public Vat(int index, BigDecimal taxedAmountPercentage, String percentage, BigDecimal amount, List<Integer> taxes) {
        this(index, new ArrayList<>(),taxedAmountPercentage, percentage, amount);
        this.taxes.addAll(taxes);
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public List<Integer> getLines() {
        return lines;
    }


}
