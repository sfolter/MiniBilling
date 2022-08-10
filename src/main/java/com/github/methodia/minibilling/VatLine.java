package com.github.methodia.minibilling;

import java.math.BigDecimal;

public class VatLine {
    private final int index;
    private final int lineIndex;
    private final BigDecimal vatPercentage;
    private final BigDecimal amount;

    public VatLine(int index, int lineIndex, BigDecimal vatPercentage, BigDecimal amount) {
        this.index = index;
        this.lineIndex = lineIndex;
        this.vatPercentage = vatPercentage;
        this.amount = amount;
    }

    public int getIndex() {
        return index;
    }

    public int getLineIndex() {
        return lineIndex;
    }

    public BigDecimal getVatPercentage() {
        return vatPercentage;
    }

    public BigDecimal getAmount() {
        return amount;
    }
}

