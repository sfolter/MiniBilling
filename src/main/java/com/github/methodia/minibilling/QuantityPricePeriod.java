package com.github.methodia.minibilling;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author Miroslav Kovachev
 * 28.07.2022
 * Methodia Inc.
 */
public class QuantityPricePeriod {
    private final LocalDateTime start;
    private final LocalDateTime end;
    private final BigDecimal price;
    private final BigDecimal quantity;

    public QuantityPricePeriod(LocalDateTime start, LocalDateTime end, BigDecimal price, BigDecimal quantity) {
        this.start = start;
        this.end = end;
        this.price = price;
        this.quantity = quantity;
    }

    public LocalDateTime getStart() {
        return start;
    }public LocalDateTime getEnd() {return end;}

    public BigDecimal getPrice() {
        return price;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }
}
