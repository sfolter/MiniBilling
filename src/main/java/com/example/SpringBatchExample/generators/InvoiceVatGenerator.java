package com.example.SpringBatchExample.generators;

import com.example.SpringBatchExample.Percentages;
import com.example.SpringBatchExample.models.InvoiceLine;
import com.example.SpringBatchExample.models.Tax;
import com.example.SpringBatchExample.models.Vat;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;


public class InvoiceVatGenerator implements VatGenerator {

    BigDecimal vatAmount = BigDecimal.ZERO;

    @Override
    public List<Vat> generateVat(final InvoiceLine invoiceLine, final List<Tax> taxes) {

        final List<Integer> linesOfVat = new ArrayList<>();
        linesOfVat.add(invoiceLine.getIndex());

        final List<Percentages> vatPercentage = new ArrayList<>();
        final Percentages percentage1 = new Percentages(new BigDecimal("0.6"), new BigDecimal("0.2"));
        final Percentages percentage2 = new Percentages(new BigDecimal("0.4"), new BigDecimal("0.1"));

        vatPercentage.add(percentage1);
        vatPercentage.add(percentage2);

        final List<Vat> vats = new ArrayList<>();
        int index = 0;
        for (final Percentages p : vatPercentage) {
            index++;
            final List<Integer> taxes1 = new ArrayList<>();

            final BigDecimal taxedAmountPercentage = p.getTaxedAmountPercentage().multiply(new BigDecimal("100"))
                    .setScale(0, RoundingMode.HALF_UP);
            final BigDecimal percentage = p.getPercentage().multiply(new BigDecimal("100"))
                    .setScale(0, RoundingMode.HALF_UP);

            vats.add(new Vat(index + linesOfVat.size(), linesOfVat, taxes1, taxedAmountPercentage,
                    percentage, vatAmount(invoiceLine, p)));

        }
        return vats;
    }

    public List<Vat> taxWithVat(final List<Tax> taxes) {
        final int size = 0;
        final List<Integer> linesOfVat = new ArrayList<>();
        final List<Vat> taxWithVat = new ArrayList<>();

        for (final Tax t : taxes) {

            final List<Integer> linesOfTax = new ArrayList<>();

            final BigDecimal taxedAmountPercentage = new BigDecimal("1");
            final BigDecimal percentage = new BigDecimal("0.2");
            final BigDecimal amount = t.getAmount().multiply(percentage).multiply(taxedAmountPercentage)
                    .setScale(2, RoundingMode.HALF_UP);

            linesOfTax.add(t.getLines().get(0));
            taxWithVat.add(new Vat(size + taxWithVat.size() + 1, linesOfVat, linesOfTax, taxedAmountPercentage.multiply(
                    new BigDecimal("100")),
                    percentage.multiply(new BigDecimal("100")).setScale(0, RoundingMode.HALF_UP), amount));

        }

        return taxWithVat;
    }


    private BigDecimal vatAmount(final InvoiceLine invoiceLine, final Percentages percentage) {
        return vatAmount = invoiceLine.getAmount().multiply(percentage.getTaxedAmountPercentage())
                .multiply(percentage.getPercentage())
                .setScale(2, RoundingMode.HALF_UP);
    }
}

