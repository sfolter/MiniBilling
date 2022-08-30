package com.github.methodia.minibilling;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

public class QuantityPricePeriod {

    private final ZonedDateTime start;
    private final ZonedDateTime end;
    private final Price price;
    private final BigDecimal quantity;
    private final User user;

    public QuantityPricePeriod(final ZonedDateTime start, final ZonedDateTime end, final Price price,
                               final BigDecimal quantity, final User user) {
        this.start = start;
        this.end = end;
        this.price = price;
        this.quantity = quantity;
        this.user = user;
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

    public User getUser() {
        return user;
    }
}
