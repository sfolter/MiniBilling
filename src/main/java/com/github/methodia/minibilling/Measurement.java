package com.github.methodia.minibilling;

import com.github.methodia.minibilling.entityClasses.User;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record Measurement(LocalDateTime start, LocalDateTime end, BigDecimal value, User user) {

}
