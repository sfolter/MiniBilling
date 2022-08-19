package com.github.methodia.minibilling;

import java.math.BigDecimal;

public class Taxes {

    private final int index;
    private final int lines;
    private final String name;
    private final int quantity;
    private final String unit;
    private final BigDecimal price;
    private final BigDecimal amount;

    public Taxes(final int index, final int lines, final String name, final int quantity, final String unit,
                 final BigDecimal price, final BigDecimal amount) {
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

    public int getLines() {
        return lines;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
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
