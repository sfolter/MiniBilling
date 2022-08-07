package com.github.methodia.minibilling;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

public class QuantityPricePeriod {
    private ZonedDateTime start;
    private ZonedDateTime end;
    private Price price;
    private BigDecimal quantity;

    public QuantityPricePeriod(ZonedDateTime start, ZonedDateTime end, Price price, BigDecimal quantity) {
        this.start = start;
        this.end = end;
        this.price = price;
        this.quantity = quantity;
    }

    public ZonedDateTime getStart() {
        return start;
    }

    public ZonedDateTime getEnd() {
        return end;
    }

    public Price getPrice() {
        return price;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }
}
