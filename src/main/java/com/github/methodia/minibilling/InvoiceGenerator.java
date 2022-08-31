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

    public Invoice generate(final List<Measurement> measurements, final long documentNumber,
                            final LocalDate borderDate,final CurrencyCalculator currencyCalculator, final String fromCurrency, final String toCurrency) {

        final InvoiceLineGenerator invoiceLineGenerator = new InvoiceLineGenerator();
        final VatGenerator vatGenerator = new VatGenerator(ExampleInputInformation.vatPercentages());

        final LocalDateTime documentDate = LocalDateTime.now();
        final String docNumber = String.valueOf(documentNumber);
        final String consumer = measurements.get(0).getUser().getName();
        final String reference = measurements.get(0).getUser().getRef();

        final List<InvoiceLine> invoiceLines = invoiceLineGenerator.createInvoiceLine(measurements, borderDate,
                currencyCalculator,fromCurrency, toCurrency);
        final TaxGenerator taxGenerator = new TaxStandingGenerator(invoiceLines);
        final List<Tax> taxes = taxGenerator.generate();
        final List<Vat> vat = vatGenerator.generate(invoiceLines, taxes);

        final BigDecimal taxTotalAmount = taxes.stream()
                .map(Tax::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        final BigDecimal totalAmount = invoiceLineGenerator.getTotalAmountLines()
                .add(taxTotalAmount.stripTrailingZeros());

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
