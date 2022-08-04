package com.github.methodia.minibilling;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author Miroslav Kovachev
 * 28.07.2022
 * Methodia Inc.
 */
public class Measurement {
    private final LocalDateTime start;
    private final LocalDateTime end;
    private final BigDecimal value;
    private final User user;

    public Measurement(LocalDateTime start, LocalDateTime end, BigDecimal value, User user) {
        this.start = start;
        this.end = end;
        this.value = value;
        this.user = user;
    }

    public LocalDateTime getStart() {
        return start;
    }


    public LocalDateTime getEnd() {
        return end;
    }

    public BigDecimal getValue() {
        return value;
    }

    public User getUser() {
        return user;
    }
}
