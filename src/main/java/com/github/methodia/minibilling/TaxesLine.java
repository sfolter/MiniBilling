package com.github.methodia.minibilling;

import java.math.BigDecimal;

/**
 * @author Todor Todorov
 * @Date 11.08.2022
 * Methodia Inc.
 */
public class TaxesLine {
    private final int index;
    private final int linesIndex;
    private final String name;
    private final long daysQuantity;
    private final String unit;
    private final BigDecimal price;
    private final BigDecimal amount;

    public TaxesLine(int index, int linesIndex, String name, long daysQuantity, String unit, BigDecimal price, BigDecimal amount) {
        this.index = index;
        this.linesIndex = linesIndex;
        this.name = name;
        this.daysQuantity = daysQuantity;
        this.unit = unit;
        this.price = price;
        this.amount = amount;
    }

    public int getIndex() {
        return index;
    }

    public int getLinesIndex() {
        return linesIndex;
    }

    public String getName() {
        return name;
    }

    public long getDaysQuantity() {
        return daysQuantity;
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
