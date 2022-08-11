package com.github.methodia.minibilling;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class InvoiceGenerator {
    private final User user;
    private final Collection<Measurement> measurements;
    private final Collection<Price> prices;

    public InvoiceGenerator(User user, Collection<Measurement> measurements, Collection<Price> prices) {
        this.user = user;
        this.measurements = measurements;
        this.prices = prices;
    }

    public Invoice generate(long documentNumber, String borderTime) {
        ProportionalMeasurementDistributor proportionalMeasurementDistributor = new ProportionalMeasurementDistributor(measurements, prices);
        Collection<QuantityPricePeriod> quantityPricePeriods = proportionalMeasurementDistributor.distribute();

        List<InvoiceLine> invoiceLines = new ArrayList<>();
        List<Vat> vat = new ArrayList<>();
        List<Tax> taxes = new ArrayList<>();
        BigDecimal totalAmount = BigDecimal.ZERO;
        BigDecimal totalAmountWithVat = BigDecimal.ZERO;
        LocalDate borderDate = Formatter.parseBorder(borderTime);

        for (QuantityPricePeriod qpp : quantityPricePeriods) {

            if (qpp.getEnd().toLocalDate().isBefore(borderDate)) {

                InvoiceLine invoiceLine = createInvoiceLine(invoiceLines.size() + 1, qpp);
                invoiceLines.add(invoiceLine);
                totalAmount = totalAmount.add(invoiceLine.getAmount()).stripTrailingZeros();

                Tax tax = createTax(taxes.size() + 1, invoiceLine);
                taxes.add(tax);

                BigDecimal vatAmount = totalAmount.multiply(BigDecimal.valueOf(0.2)).setScale(2, RoundingMode.HALF_UP)
                        .stripTrailingZeros();
                totalAmountWithVat = totalAmount.add(vatAmount).stripTrailingZeros();
                Vat vat1 = createVat(vat.size() + 1, invoiceLine);
                vat.add(vat1);
            }
        }
        LocalDateTime documentDate = LocalDateTime.now();
        String docNumber = String.valueOf(documentNumber);
        String consumer = user.getName();
        String reference = user.getRef();


        return new Invoice(documentDate, docNumber, consumer, reference, totalAmount, totalAmountWithVat, invoiceLines, taxes, vat);
    }

    private InvoiceLine createInvoiceLine(int lineIndex, QuantityPricePeriod qpp) {

        BigDecimal quantity = qpp.getQuantity();
        LocalDateTime start = qpp.getStart().toLocalDateTime();
        LocalDateTime end = qpp.getEnd().toLocalDateTime();
        String product = qpp.getPrice().getProduct();
        BigDecimal price = qpp.getPrice().getValue();
        int priceList = user.getPriceListNumber();
        BigDecimal amount = qpp.getQuantity().multiply(qpp.getPrice().getValue()).setScale(2, RoundingMode.HALF_UP).stripTrailingZeros();

        return new InvoiceLine(lineIndex, quantity, start, end,
                product, price, priceList, amount);
    }

    private Vat createVat(int index, InvoiceLine invoiceLine) {

        List<Integer> vattedLines = new ArrayList<>();
        vattedLines.add(invoiceLine.getIndex());

        BigDecimal vatAmount = invoiceLine.getAmount().multiply(BigDecimal.valueOf(0.2)).setScale(2, RoundingMode.HALF_UP)
                .stripTrailingZeros();

        return new Vat(index, vattedLines, vatAmount);
    }

    private Tax createTax(int index, InvoiceLine invoiceLine) {

        long quantityTax = invoiceLine.getLineStart().until(invoiceLine.getLineEnd(), ChronoUnit.DAYS)+1;
        BigDecimal amount = BigDecimal.valueOf(quantityTax).multiply(BigDecimal.valueOf(1.6));
        List<Integer> taxedLines = new ArrayList<>();
        taxedLines.add(invoiceLine.getIndex());
        return new Tax(index, taxedLines, quantityTax, amount);
    }
}
