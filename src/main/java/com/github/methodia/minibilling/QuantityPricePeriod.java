package com.github.methodia.minibilling;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class QuantityPricePeriod {

    private final LocalDateTime start;
    private final LocalDateTime end;
    private final Price price;
    private final BigDecimal quantity;
    private final User user;

    public QuantityPricePeriod(LocalDateTime start, LocalDateTime end, Price price, BigDecimal quantity, User user) {
        this.start = start;
        this.end = end;
        this.price = price;
        this.quantity = quantity;
        this.user = user;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public LocalDateTime getEnd() {
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
