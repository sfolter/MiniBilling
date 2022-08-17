package com.github.methodia.minibilling;

import java.math.BigDecimal;
import java.util.List;

public class Tax {
    private final int index;
    private final List<Integer> lineIndex;
    private final String name = "Standing charge";
    private final long quantity;
    private final String unit = "days";
    private final BigDecimal price = BigDecimal.valueOf(1.6);
    private final BigDecimal amount;

    public Tax(int index, List<Integer> lineIndex, long quantity, BigDecimal amount) {
        this.index = index;
        this.lineIndex = lineIndex;
        this.quantity = quantity;
        this.amount = amount;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public BigDecimal getAmount() {
        return amount;
    }
}
