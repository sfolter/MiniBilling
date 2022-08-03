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
    private  User user;
    private String product;

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

    public String getProduct() { return product;}
}
