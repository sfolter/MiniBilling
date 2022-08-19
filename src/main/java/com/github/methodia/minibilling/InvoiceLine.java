package com.github.methodia.minibilling;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record InvoiceLine(int index, BigDecimal quantity, LocalDateTime start, LocalDateTime end, String product,
                          BigDecimal price, int priceList, BigDecimal amount) {
}