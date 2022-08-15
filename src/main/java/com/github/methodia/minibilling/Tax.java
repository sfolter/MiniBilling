package com.github.methodia.minibilling;

import java.math.BigDecimal;
import java.util.List;

public class Tax {
    private final int index;
    private final List<Integer> lines;
    private final String name;
    private final BigDecimal quantity;
    private final String unit;
    private final BigDecimal price;
    private final BigDecimal amount;

    public Tax(int index, List<Integer> lines, String name, BigDecimal quantity, String unit, BigDecimal price, BigDecimal amount) {
        this.index = index;
        this.lines = lines;
        this.name = name;
        this.quantity = quantity;
        this.unit = unit;
        this.price = price;
        this.amount = amount;
    }

    public int getIndex() {
        return index;
    }

    public List<Integer> getLines() {
        return lines;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public String getUnit() {
        return unit;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public BigDecimal getAmount() {
        return amount;
    }
}
