package com.github.methodia.minibilling;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * @author Miroslav Kovachev
 * 28.07.2022
 * Methodia Inc.
 */
public class Price {
    private static String product;
    private static LocalDate start;
    private static LocalDate end;
    private static BigDecimal value;

    public Price(String product, LocalDate start, LocalDate end, BigDecimal value) {
        this.product = product;
        this.start = start;
        this.end = end;
        this.value = value;
    }

    public static String getProduct() {
        return product;
    }

    public static LocalDate getStart() {
        return start;
    }

    public static LocalDate getEnd() {
        return end;
    }

    public static BigDecimal getValue() {
        return value;
    }
}

