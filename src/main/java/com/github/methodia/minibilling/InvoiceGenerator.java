package com.github.methodia.minibilling;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class InvoiceGenerator {

    public Invoice generate(LocalDateTime dateReportingTo, List<Measurement> measurements, String currencyFrom,
                            String currencyTo, Converter exchangedTotalAmount) {

        ProportionalMeasurementDistributor proportionalMeasurementDistributor = new ProportionalMeasurementDistributor(
                measurements);
        Collection<QuantityPricePeriod> quantityPricePeriods = proportionalMeasurementDistributor.distribute();

        List<InvoiceLine> invoiceLines = new ArrayList<>();

        int index;

        BigDecimal totalAmount = BigDecimal.ZERO;
        BigDecimal totalAmountWithVat = BigDecimal.ZERO;
        List<Tax> taxes = new ArrayList<>();
        BigDecimal exchangedTotalAmount1 = BigDecimal.ZERO;
        InvoiceLineGenerator invoiceLineGenerator = new InvoiceLineGenerator();
        InvoiceVatGenerator vatGenerator = new InvoiceVatGenerator();
        InvoiceTaxGenerator taxGenerator = new InvoiceTaxGenerator();
        List<Vat> vats = new ArrayList<>();

        for (QuantityPricePeriod qpp : quantityPricePeriods) {
            if (dateReportingTo.compareTo(qpp.getEnd()) >= 0) {

                index = invoiceLines.size() + 1;

                InvoiceLine invoiceLine = invoiceLineGenerator.generateInvoiceLine(index, qpp, qpp.getUser());

                invoiceLines.add(invoiceLine);
                Tax tax = taxGenerator.generateTaxes(invoiceLine);
                taxes.add(tax);
                List<Vat> vat = vatGenerator.generateVat(invoiceLine, taxes);

                totalAmount = totalAmount.add(invoiceLine.getAmount()).add(tax.getAmount())
                        .setScale(2, RoundingMode.HALF_UP).stripTrailingZeros();

                //                Converter currencyConverter = new CurrencyConverter();
                //                BigDecimal currencyRate = currencyConverter.convertTo(currencyFrom, currencyTo, totalAmount);
                //                exchangedTotalAmount1 = totalAmount.multiply(currencyRate).setScale(2, RoundingMode.HALF_UP);


                vats.addAll(vat);
                totalAmountWithVat = totalAmountWithVat.add(vats.get(0).getAmount().add(invoiceLine.getAmount()))
                        .setScale(2, RoundingMode.HALF_UP);
            }

        }
        vats.addAll(vatGenerator.taxWithVat(taxes));

        return new Invoice(Invoice.getDocumentNumber(), measurements.get(0).getUser().getName(),
                measurements.get(0).getUser().getRef(), totalAmount, totalAmountWithVat, currencyFrom, currencyTo,
                exchangedTotalAmount1, invoiceLines, taxes, vats);

    }


}


