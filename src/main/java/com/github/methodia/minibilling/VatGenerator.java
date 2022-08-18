package com.github.methodia.minibilling;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class VatGenerator {
    private final List<VatPercentage> percentageList;

    public VatGenerator(List<VatPercentage> percentageList) {
        this.percentageList = percentageList;
    }

    List<Vat> generate(List<InvoiceLine> invoiceLines, List<Tax> taxes) {
        List<Vat> vat = new ArrayList<>();
        vat.addAll(calculateLineVat(invoiceLines));
        vat.addAll(calculateTaxVat(vat.size(), taxes));
        return vat;
    }

    private List<Vat> calculateLineVat(List<InvoiceLine> invoiceLines) {
        List<Vat> linesVats = new ArrayList<>();
        List<Integer> taxes = new ArrayList<>();

        for (InvoiceLine invoiceLine : invoiceLines) {
            List<Integer> vattedLines = new ArrayList<>();
            vattedLines.add(invoiceLine.getIndex());
            for (VatPercentage percentages : percentageList) {

                BigDecimal vatAmount = (invoiceLine.getAmount().multiply(percentages.getTaxedAmountPercentage()))
                        .multiply(percentages.getPercentage()).setScale(2, RoundingMode.HALF_UP)
                        .stripTrailingZeros();
                linesVats.add(new Vat(linesVats.size() + 1, vattedLines, taxes, percentages.getTaxedAmountPercentage(), percentages.getPercentage(), vatAmount));
            }
        }
        return linesVats;
    }

    private List<Vat> calculateTaxVat(int index, List<Tax> taxes) {
        List<Integer> vattedLines = new ArrayList<>();
        List<Vat> taxesVats = new ArrayList<>();

        for (Tax tax : taxes) {
            List<Integer> taxedLines = new ArrayList<>();

            BigDecimal taxedAmountPercentage = new BigDecimal(1);
            BigDecimal percentage = new BigDecimal("0.2");
            BigDecimal vatAmount = (tax.getAmount().multiply(taxedAmountPercentage)).multiply(percentage).setScale(2, RoundingMode.HALF_UP);
            taxedLines.add(tax.getLineIndex().get(0));
            taxesVats.add(new Vat(index + taxesVats.size() + 1, vattedLines, taxedLines, taxedAmountPercentage, percentage, vatAmount));
        }
        return taxesVats;
    }
}
