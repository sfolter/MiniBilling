package com.github.methodia.minibilling;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class InvoiceVatGenerator implements VatGenerator {

    List<Tax> taxes = new ArrayList<>();
    BigDecimal vatAmount = BigDecimal.ZERO;

    public List<Vat> generateVat(InvoiceLine invoiceLine) {
        List<Integer> linesOfVat = new ArrayList<>();
        linesOfVat.add(invoiceLine.getIndex());

        List<Percentages> vatPercentage = new ArrayList<>();
        Percentages percentage1 = new Percentages(BigDecimal.valueOf(0.6), BigDecimal.valueOf(0.2));
        Percentages percentage2 = new Percentages(BigDecimal.valueOf(0.4), BigDecimal.valueOf(0.1));
        vatPercentage.add(percentage1);
        vatPercentage.add(percentage2);

        ;

        return vatPercentage.stream()
                .map(p -> new Vat(invoiceLine.getIndex(), linesOfVat, taxes, p.getTaxedAmountPercentage(),
                        p.getPercentage(), extracted(invoiceLine,p))).toList();


    }

    private BigDecimal extracted(InvoiceLine invoiceLine,Percentages percentage) {
       return vatAmount = invoiceLine.getAmount().multiply(percentage.getTaxedAmountPercentage()).multiply(percentage.getPercentage())
                .setScale(2, RoundingMode.HALF_UP);
    }
}

