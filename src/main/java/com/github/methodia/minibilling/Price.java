package com.github.methodia.minibilling;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * @author Miroslav Kovachev
 * 28.07.2022
 * Methodia Inc.
 */
public record Price(String product, LocalDate start, LocalDate end, BigDecimal value) {

}

