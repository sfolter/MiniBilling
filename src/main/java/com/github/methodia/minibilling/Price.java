package com.github.methodia.minibilling;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZonedDateTime;

public class Price {
    private String product;
    private ZonedDateTime start;
    private ZonedDateTime end;
    private BigDecimal value;

    public Price(String product, ZonedDateTime start, ZonedDateTime end, BigDecimal value) {
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
