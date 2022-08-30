package com.github.methodia.minibilling;

import java.math.BigDecimal;
import java.util.List;

public class Tax {

    private final int index;
    private final List<Integer> lineIndex;
    private String name;
    private final long quantity;
    private String unit;
    private BigDecimal price;
    private final BigDecimal amount;



    public Tax(final int index, final List<Integer> lineIndex, final String name, final long quantity,
               final String unit, final BigDecimal price,
               final BigDecimal amount) {
        this.index = index;
        this.lineIndex = lineIndex;
        this.name = name;
        this.quantity = quantity;
        this.unit = unit;
        this.price = price;
        this.amount = amount;
    }

    public List<Integer> getLineIndex() {
        return lineIndex;
    }

    public long getQuantity() {
        return quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public BigDecimal getAmount() {
        return amount;
    }

}
