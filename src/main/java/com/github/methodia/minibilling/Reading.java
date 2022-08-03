package com.github.methodia.minibilling;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

/**
 * @author Miroslav Kovachev
 * 28.07.2022
 * Methodia Inc.
 */
public class Reading {
    private ZonedDateTime time;
    private BigDecimal value;
    private String product;
    private User user;

    public Reading(User user, String product, ZonedDateTime time, BigDecimal value) {
        this.time = time;
        this.value = value;
        this.product = product;
        this.user = user;
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
}
