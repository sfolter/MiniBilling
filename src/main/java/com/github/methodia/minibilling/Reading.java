package com.github.methodia.minibilling;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author Miroslav Kovachev
 * 25.07.2022
 * Methodia Inc.
 */
public class Reading {
    private String clientReference;
    private String productKey;
    private LocalDateTime dateTime;
    private BigDecimal value;

    public Reading(String clientReference, String productKey, LocalDateTime dateTime, BigDecimal value) {
        this.clientReference = clientReference;
        this.productKey = productKey;
        this.dateTime = dateTime;
        this.value = value;
    }

    public String getClientReference() {
        return clientReference;
    }

    public String getProductKey() {
        return productKey;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public BigDecimal getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Reads{" +
                "clientReference='" + clientReference + '\'' +
                ", productKey='" + productKey + '\'' +
                ", dateTime=" + dateTime +
                ", value=" + value +
                '}';
    }
}
