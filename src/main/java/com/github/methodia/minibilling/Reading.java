package com.github.methodia.minibilling;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

public class Reading {
    private final String referentNumber;
    private final ZonedDateTime time;
    private final BigDecimal value;
    private final User user;

    public Reading(String referentNumber, ZonedDateTime time, BigDecimal value, User user) {
        this.referentNumber = referentNumber;
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
