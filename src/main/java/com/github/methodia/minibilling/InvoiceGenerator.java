package com.github.methodia.minibilling;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class InvoiceGenerator {

//    private final String currency;
//
//    public InvoiceGenerator(String currency) {
//        this.currency = currency;
//    }

    public Invoice generate(List<Measurement> measurements, long documentNumber, LocalDate borderDate) {

        InvoiceLineGenerator invoiceLineGenerator = new InvoiceLineGenerator();
        TaxGenerator taxGenerator = new TaxGenerator();
        VatGenerator vatGenerator = new VatGenerator(ExampleInputInformation.vatPercentages());

        LocalDateTime documentDate = LocalDateTime.now();
        String docNumber = String.valueOf(documentNumber);
        String consumer = measurements.get(0).getUser().getName();
        String reference = measurements.get(0).getUser().getRef();

        List<InvoiceLine> invoiceLines = invoiceLineGenerator.createInvoiceLine(measurements, borderDate);
        List<Tax> taxes = taxGenerator.createTaxes(invoiceLines);
        List<Vat> vat = vatGenerator.generate(invoiceLines, taxes);

        BigDecimal totalAmount = invoiceLineGenerator.getTotalAmountLines().add(taxGenerator.getTotalAmountTaxes().stripTrailingZeros());
        BigDecimal vatAmount = vat.stream()
                .map(Vat::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal totalAmountWithVat = totalAmount.add(vatAmount);

        // CurrencyCalculator currencyCalculator=new CurrencyCalculator("u4hHXxTZJcijFYOGD3M2DPintFpugci8");
        // BigDecimal totalAmountCurrency = currencyCalculator.calculateTo(currency, totalAmount.get());
        // PRIMER: String total =totalAmountCurrency+currency;
        return new Invoice(documentDate, docNumber, consumer, reference, totalAmount, totalAmountWithVat, invoiceLines, taxes, vat);
    }

}
