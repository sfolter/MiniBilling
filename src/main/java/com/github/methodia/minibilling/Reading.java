package com.github.methodia.minibilling;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Reading {
    private LocalDateTime time;
    private BigDecimal value;
    private User user;

    public Reading(LocalDateTime time, BigDecimal value, User user) {
        this.time = time;
        this.value = value;
        this.user = user;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public BigDecimal getValue() {
        return value;
    }

    public User getUser() {
        return user;
    }
}
