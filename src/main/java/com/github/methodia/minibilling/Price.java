package com.github.methodia.minibilling;

import java.math.BigDecimal;
import java.time.LocalDate;

public record Price(String product, LocalDate start, LocalDate end, BigDecimal value) {

}

