package com.github.methodia.minibilling;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

public final class Price {

    private final String product;
    private final LocalDate start;
    private final LocalDate end;
    private final BigDecimal value;

    private final int numberPriceList;

    public int getNumberPriceList() {
        return numberPriceList;
    }

    public Price(String product, LocalDate start, LocalDate end, BigDecimal value, int numberPriceList) {
        this.product = product;
        this.start = start;
        this.end = end;
        this.value = value;
        this.numberPriceList = numberPriceList;
    }

    public String product() {
        return product;
    }

    public LocalDate start() {
        return start;
    }

    public LocalDate end() {
        return end;
    }

    public BigDecimal value() {
        return value;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        var that = (Price) obj;
        return Objects.equals(this.product, that.product) &&
                Objects.equals(this.start, that.start) &&
                Objects.equals(this.end, that.end) &&
                Objects.equals(this.value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(product, start, end, value);
    }

    @Override
    public String toString() {
        return "Price[" +
                "product=" + product + ", " +
                "start=" + start + ", " +
                "end=" + end + ", " +
                "value=" + value + ']';
    }


}

