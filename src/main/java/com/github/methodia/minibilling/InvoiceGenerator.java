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

//    private final String currency;
//
//    public InvoiceGenerator(String currency) {
//        this.currency = currency;
//    }

    public Invoice generate(List<Measurement> measurements, long documentNumber, LocalDate borderDate) {

        List<Vat> vat = new ArrayList<>();
        List<Tax> taxes = new ArrayList<>();
        AtomicReference<BigDecimal> totalAmount = new AtomicReference<>(BigDecimal.ZERO);
        AtomicReference<BigDecimal> totalAmountWithVat = new AtomicReference<>(BigDecimal.ZERO);
        VatGenerator vatGenerator = new VatGenerator(ExampleInputInformation.vatPercentages());
        InvoiceLineGenerator invoiceLineGenerator = new InvoiceLineGenerator();
        List<InvoiceLine> invoiceLines = invoiceLineGenerator.createInvoiceLine(measurements, borderDate);
        for (InvoiceLine invoiceLine : invoiceLines) {
            totalAmount.set(totalAmount.get().add(invoiceLine.getAmount()).stripTrailingZeros());

            Tax tax = createTax(taxes.size() + 1, invoiceLine);
            taxes.add(tax);
            totalAmount.set(totalAmount.get().add(tax.getAmount()).stripTrailingZeros());
            vat = vatGenerator.generate(invoiceLine.getIndex(), invoiceLine.getAmount(), tax.getAmount());

        }
        BigDecimal vatAmount = vat.stream()
                .map(Vat::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        totalAmountWithVat.set(totalAmount.get().add(vatAmount));

        LocalDateTime documentDate = LocalDateTime.now();
        String docNumber = String.valueOf(documentNumber);
        String consumer = measurements.get(0).getUser().getName();
        String reference = measurements.get(0).getUser().getRef();

        // CurrencyCalculator currencyCalculator=new CurrencyCalculator("u4hHXxTZJcijFYOGD3M2DPintFpugci8");
        // BigDecimal totalAmountCurrency = currencyCalculator.calculateTo(currency, totalAmount.get());
        // PRIMER: String total =totalAmountCurrency+currency;
        return new Invoice(documentDate, docNumber, consumer, reference, totalAmount.get(), totalAmountWithVat.get(), invoiceLines, taxes, vat);
    }

    private Tax createTax(int index, InvoiceLine invoiceLine) {

        List<Integer> taxedLines = new ArrayList<>();
        taxedLines.add(invoiceLine.getIndex());
        long quantityTax = invoiceLine.getLineStart().until(invoiceLine.getLineEnd(), ChronoUnit.DAYS) + 1;
        BigDecimal amount = BigDecimal.valueOf(quantityTax).multiply(BigDecimal.valueOf(1.6));
        return new Tax(index, taxedLines, quantityTax, amount);
    }
}
