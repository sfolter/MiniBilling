package com.github.methodia.minibilling;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class InvoiceGenerator {
    public Invoice generate(LocalDateTime dateReportingTo, List<Measurement> measurements) {

        ProportionalMeasurementDistributor proportionalMeasurementDistributor = new ProportionalMeasurementDistributor(measurements);
        Collection<QuantityPricePeriod> quantityPricePeriods = proportionalMeasurementDistributor.distribute();

        List<InvoiceLine> invoiceLines = new ArrayList<>();
        List<Vat> vat = new ArrayList<>();
        List<Tax> taxes = new ArrayList<>();

        int index;

        BigDecimal totalAmount = BigDecimal.ZERO;
        BigDecimal totalAmountWithVat = BigDecimal.ZERO;

        InvoiceLineGenerator invoiceLineGenerator = new InvoiceLineGenerator();
        VatGenerator vatGenerator = new VatGenerator();
        TaxGenerator taxGenerator = new TaxGenerator();

        for (QuantityPricePeriod qpp : quantityPricePeriods) {
            if (dateReportingTo.compareTo(qpp.getEnd()) >= 0) {

                index = invoiceLines.size() + 1;

                InvoiceLine invoiceLine = invoiceLineGenerator.createInvoiceLine(index, qpp, qpp.getUser());

                invoiceLines.add(invoiceLine);
                totalAmount = totalAmount.add(invoiceLine.getAmount()).setScale(2, RoundingMode.HALF_UP).stripTrailingZeros();

                vat.add(vatGenerator.createVat(invoiceLine));
                totalAmountWithVat = totalAmountWithVat.add(vat.get(0).getAmount().add(invoiceLine.getAmount())).setScale(2, RoundingMode.HALF_UP).stripTrailingZeros();

                Tax tax = taxGenerator.createTaxes(invoiceLine);
                taxes.add(tax);

            }
        }

        return new Invoice(Invoice.getDocumentNumber(), measurements.get(0).getUser().getName(), measurements.get(0).getUser().getRef(), totalAmount, totalAmountWithVat, invoiceLines, vat, taxes);

    }


}