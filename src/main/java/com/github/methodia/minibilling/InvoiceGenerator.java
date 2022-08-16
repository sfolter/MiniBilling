package com.github.methodia.minibilling;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class InvoiceGenerator {
    private final List<Measurement> measurements;
    //private final String currency;

    public InvoiceGenerator( List<Measurement> measurements) {
        this.measurements = measurements;
       // this.currency = currency;
    }

    public Invoice generate(long documentNumber, String borderTime) {
        ProportionalMeasurementDistributor proportionalMeasurementDistributor = new ProportionalMeasurementDistributor(measurements);
        Collection<QuantityPricePeriod> quantityPricePeriods = proportionalMeasurementDistributor.distribute();

        List<InvoiceLine> invoiceLines = new ArrayList<>();
        List<Vat> vat = new ArrayList<>();
        List<Tax> taxes = new ArrayList<>();
        AtomicReference<BigDecimal> totalAmount = new AtomicReference<>(BigDecimal.ZERO);
        AtomicReference<BigDecimal> totalAmountWithVat = new AtomicReference<>(BigDecimal.ZERO);
        LocalDate borderDate = Formatter.parseBorder(borderTime);


        quantityPricePeriods.stream()
                .filter(qpp -> qpp.getEnd().toLocalDate().isBefore(borderDate))
                .forEach(q -> {
                    InvoiceLine invoiceLine = createInvoiceLine(invoiceLines.size() + 1, q);
                    invoiceLines.add(invoiceLine);
                    totalAmount.set(totalAmount.get().add(invoiceLine.getAmount()).stripTrailingZeros());

                    Tax tax = createTax(taxes.size() + 1, invoiceLine);
                    taxes.add(tax);

                    Vat vat1 = createVat(vat.size() + 1, invoiceLine);
                    vat.add(vat1);

                    totalAmountWithVat.set(totalAmount.get().add(vat1.getAmount()).stripTrailingZeros());
                });

        LocalDateTime documentDate = LocalDateTime.now();
        String docNumber = String.valueOf(documentNumber);
        String consumer = measurements.get(0).getUser().getName();
        String reference = measurements.get(0).getUser().getRef();

        // CurrencyCalculator currencyCalculator=new CurrencyCalculator("u4hHXxTZJcijFYOGD3M2DPintFpugci8");
        // BigDecimal totalAmountCurrency = currencyCalculator.calculateTo(currency, totalAmount.get());
        // PRIMER: String total =totalAmountCurrency+currency;
        return new Invoice(documentDate, docNumber, consumer, reference, totalAmount.get(), totalAmountWithVat.get(), invoiceLines, taxes, vat);
    }

    private InvoiceLine createInvoiceLine(int lineIndex, QuantityPricePeriod qpp) {

        BigDecimal quantity = qpp.getQuantity();
        LocalDateTime start = qpp.getStart().toLocalDateTime();
        LocalDateTime end = qpp.getEnd().toLocalDateTime();
        String product = qpp.getPrice().getProduct();
        BigDecimal price = qpp.getPrice().getValue();
        int priceList = qpp.getUser().getPriceListNumber();
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

        List<Integer> taxedLines = new ArrayList<>();
        taxedLines.add(invoiceLine.getIndex());
        long quantityTax = invoiceLine.getLineStart().until(invoiceLine.getLineEnd(), ChronoUnit.DAYS) + 1;
        BigDecimal amount = BigDecimal.valueOf(quantityTax).multiply(BigDecimal.valueOf(1.6));
        return new Tax(index, taxedLines, quantityTax, amount);
    }
}
