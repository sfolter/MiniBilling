package com.github.methodia.minibilling;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class InvoiceVatGenerator implements VatGenerator {

    BigDecimal vatAmount = BigDecimal.ZERO;

    public List<Vat> generateVat(InvoiceLine invoiceLine, List<Tax> taxes) {

        List<Integer> linesOfVat = new ArrayList<>();
        linesOfVat.add(invoiceLine.getIndex());
        List<Vat> vats = new ArrayList<>();

        List<Percentages> vatPercentage = new ArrayList<>();
        Percentages percentage1 = new Percentages(new BigDecimal("0.6"), new BigDecimal("0.2"));
        Percentages percentage2 = new Percentages(new BigDecimal("0.4"), new BigDecimal("0.1"));
        vatPercentage.add(percentage1);
        vatPercentage.add(percentage2);
        int index = 0;
        for (Percentages p : vatPercentage) {
            index++;
            List<Integer> taxes1 = new ArrayList<>();
            BigDecimal taxedAmountPercentage = p.getTaxedAmountPercentage().multiply(new BigDecimal("100"))
                    .setScale(0, RoundingMode.HALF_UP);
            BigDecimal percentage = p.getPercentage().multiply(new BigDecimal("100"))
                    .setScale(0, RoundingMode.HALF_UP);

            vats.add(new Vat(index + linesOfVat.size(), linesOfVat, taxes1, taxedAmountPercentage,
                    percentage, vatAmount(invoiceLine, p)));

        }
        return vats;
    }

    public List<Vat> taxWithVat(List<Tax> taxes) {
        int size = 0;
        List<Integer> linesOfVat = new ArrayList<>();
        List<Vat> taxWithVat = new ArrayList<>();

        for (Tax t : taxes) {

            List<Integer> linesOfTax = new ArrayList<>();

            BigDecimal taxedAmountPercentage = new BigDecimal("1");
            BigDecimal percentage = new BigDecimal("0.2");
            BigDecimal amount = t.getAmount().multiply(percentage).multiply(taxedAmountPercentage)
                    .setScale(2, RoundingMode.HALF_UP);
            linesOfTax.add(t.getLines().get(0));
            taxWithVat.add(new Vat(size + taxWithVat.size() + 1, linesOfVat, linesOfTax, taxedAmountPercentage.multiply(
                    new BigDecimal("100")),
                    percentage.multiply(new BigDecimal("100")).setScale(0, RoundingMode.HALF_UP), amount));

        }

        return taxWithVat;
    }


    private BigDecimal vatAmount(InvoiceLine invoiceLine, Percentages percentage) {
        return vatAmount = invoiceLine.getAmount().multiply(percentage.getTaxedAmountPercentage())
                .multiply(percentage.getPercentage())
                .setScale(2, RoundingMode.HALF_UP);
    }
}

