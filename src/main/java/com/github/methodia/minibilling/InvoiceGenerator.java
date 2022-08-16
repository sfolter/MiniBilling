package com.github.methodia.minibilling;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
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
        List<Integer> vatInvoiceLine = new ArrayList<>();
        BigDecimal totalAmountWithVat = BigDecimal.ZERO;

        for (QuantityPricePeriod qpp : quantityPricePeriods) {
            if (dateReportingTo.compareTo(qpp.getEnd()) >= 0) {

                index = invoiceLines.size() + 1;
                InvoiceLine invoiceLine = createInvoiceLine(index, qpp,qpp.getUser());
                invoiceLines.add(invoiceLine);
                totalAmount = totalAmount.add(invoiceLine.getAmount()).setScale(2, RoundingMode.HALF_UP).stripTrailingZeros();

                vatInvoiceLine.add(index);
                Vat v = createVat(invoiceLine);
                vat.add(v);
                totalAmountWithVat = totalAmountWithVat.add(v.getAmount().add(invoiceLine.getAmount())).setScale(2, RoundingMode.HALF_UP).stripTrailingZeros();

                Tax tax = createTaxes(invoiceLine);
                taxes.add(tax);

            }
        }

        return new Invoice(Invoice.getDocumentNumber(), measurements.get(0).getUser().getName(), measurements.get(0).getUser().getRef(), totalAmount, totalAmountWithVat, invoiceLines, vat, taxes);

    }

    private InvoiceLine createInvoiceLine(int index, QuantityPricePeriod qpp,User user) {

        BigDecimal quantity = qpp.getQuantity().setScale(2, RoundingMode.HALF_UP).stripTrailingZeros();
        LocalDateTime lineStart = qpp.getStart();
        LocalDateTime lineEnd = qpp.getEnd();
        String product = qpp.getPrice().getProduct();
        BigDecimal price = qpp.getPrice().getValue();
        int priceList = user.getNumberPricingList();
        BigDecimal amount = qpp.getQuantity().multiply(qpp.getPrice().getValue()).setScale(2, RoundingMode.HALF_UP).stripTrailingZeros();

        return new InvoiceLine(index, quantity, lineStart, lineEnd, product, price, priceList, amount);
    }

    private Vat createVat(InvoiceLine invoiceLine) {
        List<Integer> linesOfVat = new ArrayList<>();
        linesOfVat.add(invoiceLine.getIndex());

        int percentage = 20;
        BigDecimal vatAmount = invoiceLine.getAmount().multiply(BigDecimal.valueOf(percentage).multiply(BigDecimal.valueOf(0.1))).setScale(2, RoundingMode.HALF_UP)
                .stripTrailingZeros();

        return new Vat(invoiceLine.getIndex(), linesOfVat, percentage, vatAmount);
    }

    private Tax createTaxes(InvoiceLine invoiceLine) {
        List<Integer> linesOFTax = new ArrayList<>();
        linesOFTax.add(invoiceLine.getIndex());

        BigDecimal quantity = BigDecimal.valueOf(invoiceLine.getLineStart().until(invoiceLine.getLineEnd(), ChronoUnit.DAYS) + 1);
        BigDecimal taxAmount = quantity.multiply(BigDecimal.valueOf(1.6));

        return new Tax(invoiceLine.getIndex(), linesOFTax, "", quantity, "", BigDecimal.valueOf(1.6), taxAmount);
    }


}