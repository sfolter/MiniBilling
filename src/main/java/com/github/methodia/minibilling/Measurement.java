package com.github.methodia.minibilling;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

public class Measurement {
    private ZonedDateTime start;
    private ZonedDateTime end;
    private BigDecimal value;
    private User user;

    public Measurement(ZonedDateTime start, ZonedDateTime end, BigDecimal value, User user) {
        this.start = start;
        this.end = end;
        this.value = value;
        this.user = user;
    }

    public ZonedDateTime getStart() {
        return start;
    }

    public ZonedDateTime getEnd() {
        return end;
    }

    public BigDecimal getValue() {
        return value;
    }

    public User getUser() {
        return user;
    }


}
