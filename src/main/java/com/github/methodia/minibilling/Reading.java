package com.github.methodia.minibilling;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

public class Reading {
    private ZonedDateTime time;
    private BigDecimal value;
    private User user;

    public Reading(ZonedDateTime time, BigDecimal value, User user) {
        this.time = time;
        this.value = value;
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
