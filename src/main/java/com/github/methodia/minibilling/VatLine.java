package com.github.methodia.minibilling;

import java.math.BigDecimal;

public record VatLine(int index, int lineIndex, int taxesIndex, BigDecimal taxedAmountPercentage,
                      BigDecimal vatPercentage, BigDecimal amount) {
}

