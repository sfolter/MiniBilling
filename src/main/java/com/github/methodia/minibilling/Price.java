package com.github.methodia.minibilling;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * @author Miroslav Kovachev
 * 25.07.2022
 * Methodia Inc.
 */
public class Price {
    private String productKey;
    private LocalDate startDate;
    private LocalDate endDate;
    private BigDecimal price;

    public Price(String productKey, LocalDate startDate, LocalDate endDate, BigDecimal price) {
        this.productKey = productKey;
        this.startDate = startDate;
        this.endDate = endDate;
        this.price = price;
    }

    public String getProductKey() {
        return productKey;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
