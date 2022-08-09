package com.github.methodia.minibilling;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

public class Measurement {
    private final ZonedDateTime start;
    private final ZonedDateTime end;
    private final BigDecimal value;
    private final User user;

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
