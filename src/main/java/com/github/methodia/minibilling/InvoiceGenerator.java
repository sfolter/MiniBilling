package com.github.methodia.minibilling;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class InvoiceGenerator {

    //    private final String currency;
    //
    //    public InvoiceGenerator(String currency) {
    //        this.currency = currency;
    //    }

    public Invoice generate(final List<Measurement> measurements, final long documentNumber, final LocalDate borderDate) {

        final InvoiceLineGenerator invoiceLineGenerator = new InvoiceLineGenerator();
        final TaxGenerator taxGenerator = new TaxGenerator();
        final VatGenerator vatGenerator = new VatGenerator(ExampleInputInformation.vatPercentages());

        final LocalDateTime documentDate = LocalDateTime.now();
        final String docNumber = String.valueOf(documentNumber);
        final String consumer = measurements.get(0).getUser().getName();
        final String reference = measurements.get(0).getUser().getRef();

        final List<InvoiceLine> invoiceLines = invoiceLineGenerator.createInvoiceLine(measurements, borderDate);
        final List<Tax> taxes = taxGenerator.createTaxes(invoiceLines);
        final List<Vat> vat = vatGenerator.generate(invoiceLines, taxes);

        final BigDecimal totalAmount = invoiceLineGenerator.getTotalAmountLines()
                .add(taxGenerator.getTotalAmountTaxes().stripTrailingZeros());
        final BigDecimal vatAmount = vat.stream()
                .map(Vat::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        final BigDecimal totalAmountWithVat = totalAmount.add(vatAmount);

        // CurrencyCalculator currencyCalculator=new CurrencyCalculator("u4hHXxTZJcijFYOGD3M2DPintFpugci8");
        // BigDecimal totalAmountCurrency = currencyCalculator.calculateTo(currency, totalAmount.get());
        // PRIMER: String total =totalAmountCurrency+currency;
        return new Invoice(documentDate, docNumber, consumer, reference, totalAmount, totalAmountWithVat, invoiceLines,
                taxes, vat);
    }

}
