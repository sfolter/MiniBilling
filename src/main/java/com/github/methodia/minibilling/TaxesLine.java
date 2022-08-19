package com.github.methodia.minibilling;

import java.math.BigDecimal;

/**
 * @author Todor Todorov
 * @Date 11.08.2022
 * Methodia Inc.
 */
public record TaxesLine(int index, int linesIndex, String name, long daysQuantity, String unit, BigDecimal price,
                        BigDecimal amount) {
}
