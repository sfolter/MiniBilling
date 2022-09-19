package com.github.methodia.minibilling;

import com.github.methodia.minibilling.entity.InvoiceLine;
import com.github.methodia.minibilling.entity.Tax;
import com.github.methodia.minibilling.entity.Vat;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class VatGenerator {

    private final List<VatPercentage> percentageList;

    public VatGenerator(final List<VatPercentage> percentageList) {
        this.percentageList = percentageList;
    }

    List<Vat> generate(final List<InvoiceLine> invoiceLines, final List<Tax> taxes) {
        final List<Vat> vat = new ArrayList<>();
        vat.addAll(calculateLineVat(invoiceLines));
        vat.addAll(calculateTaxVat(vat.size(), taxes));
        return vat;
    }

    private List<Vat> calculateLineVat(final List<InvoiceLine> invoiceLines) {
        final List<Vat> linesVats = new ArrayList<>();
        final List<Integer> taxes = new ArrayList<>();

        for (final InvoiceLine invoiceLine : invoiceLines) {
            final List<Integer> vattedLines = new ArrayList<>();
            vattedLines.add(invoiceLine.getIndex());
            for (final VatPercentage percentages : percentageList) {
                final BigDecimal vatAmount = invoiceLine.getAmount()
                        .multiply(percentages.getTaxedAmountPercentage().divide(new BigDecimal("100")))
                        .multiply(percentages.getPercentage().divide(new BigDecimal("100")))
                        .setScale(2, RoundingMode.HALF_UP)
                        .stripTrailingZeros();
                linesVats.add(new Vat(linesVats.size() + 1, vattedLines, taxes, percentages.getTaxedAmountPercentage(),
                        percentages.getPercentage(), vatAmount));
            }
        }

        return linesVats;
    }

    private List<Vat> calculateTaxVat(final int index, final List<Tax> taxes) {
        final List<Integer> vattedLines = new ArrayList<>();
        final List<Vat> taxesVats = new ArrayList<>();

        for (final Tax tax : taxes) {
            final List<Integer> taxedLines = new ArrayList<>();

            final BigDecimal taxedAmountPercentage = new BigDecimal(1);
            final BigDecimal percentage = new BigDecimal("0.2");
            final BigDecimal vatAmount = tax.getAmount().multiply(taxedAmountPercentage).multiply(percentage)
                    .setScale(2, RoundingMode.HALF_UP);
            taxedLines.add(tax.getLineIndex().get(0));
            taxesVats.add(
                    new Vat(index + taxesVats.size() + 1, vattedLines, taxedLines, taxedAmountPercentage, percentage,
                            vatAmount));
        }
        return taxesVats;
    }
}
