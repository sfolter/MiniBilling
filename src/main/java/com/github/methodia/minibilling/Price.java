package com.github.methodia.minibilling;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

public class Price {

    private final String product;
    private final ZonedDateTime start;
    private final ZonedDateTime end;
    private final BigDecimal value;

    public Price(final String product, final ZonedDateTime start, final ZonedDateTime end, final BigDecimal value) {
        this.product = product;
        this.start = start;
        this.end = end;
        this.value = value;
    }

    public String getProduct() {
        return product;
    }

    public ZonedDateTime getStart() {
        return start;
    }

    public ZonedDateTime getEnd() {
        return end;
    }

    public BigDecimal getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Price{" +
                "product='" + product + '\'' +
                ", start=" + start +
                ", end=" + end +
                ", value=" + value +
                '}';
    }
}
