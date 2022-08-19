package com.github.methodia.minibilling;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

/**
 * @author Miroslav Kovachev
 * 28.07.2022
 * Methodia Inc.
 */
public record Reading(User user, String product, ZonedDateTime time, BigDecimal value) {
}
