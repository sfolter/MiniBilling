package com.example.SpringBatchExample.generators;

import com.example.SpringBatchExample.Converter;
import com.example.SpringBatchExample.Measurement;
import com.example.SpringBatchExample.ProportionalMeasurementDistributor;
import com.example.SpringBatchExample.QuantityPricePeriod;
import com.example.SpringBatchExample.models.Invoice;
import com.example.SpringBatchExample.models.InvoiceLine;
import com.example.SpringBatchExample.models.Tax;
import com.example.SpringBatchExample.models.Vat;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class InvoiceGenerator {

    public Invoice generate(final LocalDateTime dateReportingTo, final List<Measurement> measurements, final String currencyFrom,
                            final String currencyTo, final Converter exchangedTotalAmount) {

        final ProportionalMeasurementDistributor proportionalMeasurementDistributor = new ProportionalMeasurementDistributor(
                measurements);
        final Collection<QuantityPricePeriod> quantityPricePeriods = proportionalMeasurementDistributor.distribute();


        BigDecimal totalAmountWithVat = BigDecimal.ZERO;

        final InvoiceLineGenerator invoiceLineGenerator = new InvoiceLineGenerator();
        final List<InvoiceLine> invoiceLines = new ArrayList<>();

        final InvoiceVatGenerator vatGenerator = new InvoiceVatGenerator();
        final List<Vat> vats = new ArrayList<>();

        final InvoiceTaxGenerator taxGenerator = new InvoiceTaxGenerator();
        final List<Tax> taxes = new ArrayList<>();

        int index;
        BigDecimal totalAmount = BigDecimal.ZERO;

        for (final QuantityPricePeriod qpp : quantityPricePeriods) {
            if (dateReportingTo.compareTo(qpp.getEnd()) >= 0) {

                index = invoiceLines.size() + 1;

                final InvoiceLine invoiceLine = invoiceLineGenerator.generateInvoiceLine(index, qpp, qpp.getUser());
                invoiceLines.add(invoiceLine);

                final Tax tax = taxGenerator.generateTaxes(invoiceLine);
                taxes.add(tax);

                final List<Vat> vat = vatGenerator.generateVat(invoiceLine, taxes);

                totalAmount = totalAmount.add(invoiceLine.getAmount()).add(tax.getAmount())
                        .setScale(2, RoundingMode.HALF_UP).stripTrailingZeros();

                            /*  Converter currencyConverter = new CurrencyConverter();
                               BigDecimal currencyRate = currencyConverter.convertTo(currencyFrom, currencyTo, totalAmount);
                               exchangedTotalAmount1 = totalAmount.multiply(currencyRate).setScale(2, RoundingMode.HALF_UP);*/
                vats.addAll(vat);
            }

        }
        //  BigDecimal currencyRate = new BigDecimal("0.5117");

        vats.addAll(vatGenerator.taxWithVat(taxes));

        totalAmountWithVat = totalAmountWithVat.add(
                        vats.stream().map(Vat::getAmount).reduce(totalAmount, BigDecimal::add))
                .setScale(2, RoundingMode.HALF_UP);
        // BigDecimal convertedTotalAmountWithVat = totalAmount.multiply(currencyRate).setScale(2,RoundingMode.HALF_UP);

        return new Invoice(Invoice.getDocumentNumber(), measurements.get(0).getUser().getName(),
                measurements.get(0).getUser().getRefNumber(), totalAmount, totalAmountWithVat,
                invoiceLines, taxes, vats);

    }


}


