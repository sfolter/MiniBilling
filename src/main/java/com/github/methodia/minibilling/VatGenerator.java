package com.github.methodia.minibilling;

import com.github.methodia.minibilling.entityClasses.InvoiceLine;
import com.github.methodia.minibilling.entityClasses.Tax;
import com.github.methodia.minibilling.entityClasses.Vat;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class VatGenerator {

    public List<Vat> generate(final List<VatPercentages> vatPercentages, final List<InvoiceLine> invoiceLines, final List<Tax> taxes) {
        final List<Vat> vat = new ArrayList<>();
        vat.addAll(invoiceLinesVatGenerator(vatPercentages, invoiceLines));
        vat.addAll(taxVatGenerator(vat.size(), taxes));
        return vat;
    }

    private List<Vat> invoiceLinesVatGenerator(final List<VatPercentages> vatPercentages, final List<InvoiceLine> invoiceLines) {
        final List<Vat> vatInvoiceLines = new ArrayList<>();
        for (final InvoiceLine invoiceLine : invoiceLines) {
            final List<Integer> vattedLines = new ArrayList<>();
            vattedLines.add(invoiceLine.getIndex());

            for (final VatPercentages percentage : vatPercentages) {
                //noinspection BigDecimalMethodWithoutRoundingCalled
                final BigDecimal vatAmount = invoiceLine.getAmount()
                        .multiply(percentage.taxedAmountPercentage().divide(new BigDecimal("100")))
                        .multiply(percentage.percentage().divide(new BigDecimal("100")))
                        .setScale(2, RoundingMode.HALF_UP).stripTrailingZeros();
                vatInvoiceLines.add(
                        new Vat(vatInvoiceLines.size() + 1, vattedLines, percentage.taxedAmountPercentage(),
                                String.valueOf(percentage.percentage()), vatAmount));
            }
        }
        return vatInvoiceLines;
    }

    private List<Vat> taxVatGenerator(final int index, final List<Tax> taxes) {
        final List<Vat> vatTax = new ArrayList<>();
        for (final Tax tax : taxes) {
            final List<Integer> taxedLines = new ArrayList<>();
            final BigDecimal taxedAmountPercentage = new BigDecimal("100");
            final BigDecimal percentage = new BigDecimal("20");
            //noinspection BigDecimalMethodWithoutRoundingCalled
            final BigDecimal vatAmount = tax.getAmount().multiply(percentage).divide(taxedAmountPercentage)
                    .setScale(2, RoundingMode.HALF_UP);
            taxedLines.add(tax.getLines().get(0));
            vatTax.add(new Vat(index + vatTax.size() + 1, taxedAmountPercentage,
                    String.valueOf(percentage), vatAmount, taxedLines));
        }
        return vatTax;
    }
}
