package com.github.methodia.minibilling;

import java.math.BigDecimal;

public class VatLine {
    private final int index;
    private final int lineIndex;
    private final int taxesIndex;
    private final BigDecimal taxedAmountPercentage;
    private final BigDecimal vatPercentage;
    private final BigDecimal amount;

    public VatLine(int index, int lineIndex, int taxesIndex, BigDecimal taxedAmountPercentage, BigDecimal vatPercentage, BigDecimal amount) {
        this.index = index;
        this.lineIndex = lineIndex;
        this.taxesIndex = taxesIndex;
        this.taxedAmountPercentage = taxedAmountPercentage;
        this.vatPercentage = vatPercentage;
        this.amount = amount;
    }

    public int getIndex() {
        return index;
    }

    public int getLineIndex() {
        return lineIndex;
    }

    public int getTaxesIndex() {
        return taxesIndex;
    }

    public BigDecimal getTaxedAmountPercentage() {
        return taxedAmountPercentage;
    }

    public BigDecimal getVatPercentage() {
        return vatPercentage;
    }

    public BigDecimal getAmount() {
        return amount;
    }
}

