package com.github.methodia.minibilling;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author Miroslav Kovachev
 * 28.07.2022
 * Methodia Inc.
 */
public record Measurement(LocalDateTime start, LocalDateTime end, BigDecimal value, User user) {
}
