package com.github.methodia.minibilling;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Price {
    private String product;
    private LocalDate start;
    private LocalDate end;
    private BigDecimal value;

    public Price(String product, LocalDate start, LocalDate end, BigDecimal value) {
        this.product = product;
        this.start = start;
        this.end = end;
        this.value = value;
    }

    public String getProduct() {
        return product;
    }

    public LocalDate getStart() {
        return start;
    }

    public LocalDate getEnd() {
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
