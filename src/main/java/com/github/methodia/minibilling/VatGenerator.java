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

    List<Vat> generate(int lineIndex, BigDecimal lineAmount, BigDecimal taxAmount) {
        List<Vat> vat = new ArrayList<>();
        List<Integer> vattedLines = new ArrayList<>();
        vattedLines.add(lineIndex);
        List<Integer> taxes = new ArrayList<>();
        for (VatPercentage percentages : percentageList) {

            BigDecimal vatAmount = (lineAmount.multiply(percentages.getTaxedAmountPercentage()))
                    .multiply(percentages.getPercentage()).setScale(2, RoundingMode.HALF_UP)
                    .stripTrailingZeros();
            vat.add(new Vat(vat.size() + 1, vattedLines, taxes, percentages.getTaxedAmountPercentage(), percentages.getPercentage(), vatAmount));
        }
        vat.add(taxVatGenerate(vat.size() + 1, lineIndex, taxAmount));
        return vat;
    }

    private Vat taxVatGenerate(int index, int lineIndex, BigDecimal taxAmount) {
        List<Integer> vattedLines = new ArrayList<>();
        List<Integer> taxes = new ArrayList<>();
        taxes.add(lineIndex);
        BigDecimal taxedAmountPercentage = new BigDecimal(1);
        BigDecimal percentage = new BigDecimal("0.2");
        BigDecimal vatAmount = (taxAmount.multiply(taxedAmountPercentage)).multiply(percentage).setScale(2, RoundingMode.HALF_UP);
        return new Vat(index, vattedLines, taxes, taxedAmountPercentage, percentage, vatAmount);
    }
}
