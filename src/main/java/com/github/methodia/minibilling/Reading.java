package com.github.methodia.minibilling;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

public class Reading {
    private final ZonedDateTime time;
    private final BigDecimal value;
    private final User user;
    private final String product;

    public Reading(ZonedDateTime time, BigDecimal value, User user, String product) {
        this.time = time;
        this.value = value;
        this.user = user;
        this.product = product;
    }

    public ZonedDateTime getTime() {
        return time;
    }

    public BigDecimal getValue() {
        return value;
    }

    public User getUser() {
        return user;
    }

    public String getProduct() {
        return product;
    }
}
