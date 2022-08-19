package com.github.methodia.minibilling;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Price {

    private final String product;
    private final LocalDate start;
    private final LocalDate end;
    private final BigDecimal value;

    public Price(final String product, final LocalDate start, final LocalDate end, final BigDecimal value) {
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
}

