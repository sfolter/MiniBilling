package com.github.methodia.minibilling;

import java.math.BigDecimal;
import java.util.List;

public class Tax {

    private int index;
    private final List<Integer> lines;
    private final String name;
    private final BigDecimal quantity;
    private final String unit;
    private final BigDecimal price;
    private final BigDecimal amount;

    public Tax(int index, List<Integer> lines, BigDecimal quantity, BigDecimal amount) {
        this.index = index;
        this.lines = lines;
        this.name = "Standing charge";
        this.quantity = quantity;
        this.unit = "days";
        this.price = new BigDecimal("1.6");
        this.amount = amount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public int getIndex() {
        return index;
    }

    public List<Integer> getLines() {
        return lines;
    }
}
