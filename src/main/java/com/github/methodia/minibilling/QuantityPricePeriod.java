package com.github.methodia.minibilling;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

public class QuantityPricePeriod {
    private final ZonedDateTime start;
    private final ZonedDateTime end;
    private final Price price;
    private final BigDecimal quantity;

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
