package com.github.methodia.minibilling;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class QuantityPricePeriod {

    private final LocalDateTime start;
    private final LocalDateTime end;
    private final BigDecimal price;

    private final String product;
    private final BigDecimal quantity;

    public QuantityPricePeriod(LocalDateTime start, LocalDateTime end, BigDecimal price, String product,
                               BigDecimal quantity) {
        this.start = start;
        this.end = end;
        this.price = price;
        this.product = product;
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

    public String getProduct() {
        return product;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }
}
