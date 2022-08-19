package com.github.methodia.minibilling;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record QuantityPricePeriod(LocalDateTime start, LocalDateTime end, Price price, BigDecimal quantity) {
}