package com.github.methodia.minibilling;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class VatGenerator {

    public List<Vat> generate(List<VatPercentages> vatPercentages, List<InvoiceLine> invoiceLines, List<Tax> taxes) {
        List<Vat> vat = new ArrayList<>();
        vat.addAll(invoiceLinesVatGenerator(vatPercentages, invoiceLines));
        vat.addAll(taxVatGenerator(vat.size(), taxes));
        return vat;
    }

    private List<Vat> invoiceLinesVatGenerator(List<VatPercentages> vatPercentages, List<InvoiceLine> invoiceLines) {
        List<Vat> vatInvoiceLines = new ArrayList<>();
        for (InvoiceLine invoiceLine : invoiceLines) {
            List<Integer> vattedLines = new ArrayList<>();
            vattedLines.add(invoiceLine.getIndex());

            for (VatPercentages percentage : vatPercentages) {
                BigDecimal vatAmount = invoiceLine.getAmount()
                        .multiply(percentage.getTaxedAmountPercentage().divide(new BigDecimal("100")))
                        .multiply(percentage.getPercentage().divide(new BigDecimal("100")))
                        .setScale(2, RoundingMode.HALF_UP).stripTrailingZeros();
                vatInvoiceLines.add(
                        new Vat(vatInvoiceLines.size() + 1, vattedLines,percentage.getTaxedAmountPercentage(),
                                String.valueOf(percentage.getPercentage()), vatAmount));
            }
        }
        return vatInvoiceLines;
    }

    private List<Vat> taxVatGenerator(int index, List<Tax> taxes) {
        List<Vat> vatTax = new ArrayList<>();
        for (Tax tax : taxes) {
            List<Integer> taxedLines = new ArrayList<>();
            BigDecimal taxedAmountPercentage = new BigDecimal("100");
            BigDecimal percentage = new BigDecimal("20");
            BigDecimal vatAmount = tax.getAmount().multiply(percentage).divide(taxedAmountPercentage
                    .setScale(2, RoundingMode.HALF_UP));
            taxedLines.add(tax.getLines().get(0));
            vatTax.add(new Vat(index + vatTax.size() + 1,taxedAmountPercentage,
                    String.valueOf(percentage), vatAmount, taxedLines));
        }
        return vatTax;
    }
}