package com.github.methodia.minibilling;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class QuantityPricePeriod {
    private LocalDateTime start;
    private LocalDateTime end;
    private BigDecimal price;
    private BigDecimal quantity;

    public QuantityPricePeriod(LocalDateTime start, LocalDateTime end, BigDecimal price, BigDecimal quantity) {
        this.start = start;
        this.end = end;
        this.price = price;
        this.quantity = quantity;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }
}
