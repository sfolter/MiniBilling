package com.github.methodia.minibilling;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class VatGenerator {
    public Vat createVat(InvoiceLine invoiceLine) {
        List<Integer> linesOfVat = new ArrayList<>();
        linesOfVat.add(invoiceLine.getIndex());

        BigDecimal vatAmount = invoiceLine.getAmount().multiply(BigDecimal.valueOf(0.2)).setScale(2, RoundingMode.HALF_UP)
                .stripTrailingZeros();

        return new Vat(invoiceLine.getIndex(), linesOfVat, vatAmount);
    }
}

