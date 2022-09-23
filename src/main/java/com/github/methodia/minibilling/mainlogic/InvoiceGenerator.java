package com.github.methodia.minibilling.mainlogic;

import com.github.methodia.minibilling.currency.CurrencyCalculator;
import com.github.methodia.minibilling.ExampleInputInformation;
import com.github.methodia.minibilling.Measurement;
import com.github.methodia.minibilling.entity.Invoice;
import com.github.methodia.minibilling.entity.InvoiceLine;
import com.github.methodia.minibilling.entity.Tax;
import com.github.methodia.minibilling.entity.Vat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class InvoiceGenerator {

    private final CurrencyCalculator currencyCalculator;
    private final String fromCurrency;

    public InvoiceGenerator(final CurrencyCalculator currencyCalculator, final String fromCurrency)  {
        this.currencyCalculator = currencyCalculator;
        this.fromCurrency = fromCurrency;
    }

    public Invoice generate(final List<Measurement> measurements, final int documentNumber,
                            final LocalDate borderDate, final String toCurrency) throws RuntimeException{

            if( measurements.isEmpty()){
                throw new RuntimeException();
            }


        final InvoiceLineGenerator invoiceLineGenerator = new InvoiceLineGenerator();
        final VatGenerator vatGenerator = new VatGenerator(ExampleInputInformation.vatPercentages());

        final LocalDateTime documentDate = LocalDateTime.now();
        final String docNumber =String.valueOf(documentNumber);
        final String consumer = measurements.get(0).getUser().getName();
        final String reference = measurements.get(0).getUser().getRef();

        final List<InvoiceLine> invoiceLines = invoiceLineGenerator.createInvoiceLine(measurements, borderDate,
                currencyCalculator, fromCurrency, toCurrency);
        final TaxGenerator taxGenerator = new TaxStandingGenerator(invoiceLines, currencyCalculator, fromCurrency,
                toCurrency);
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
        return new Invoice(documentDate, docNumber, consumer, reference, totalAmount, totalAmountWithVat, toCurrency,
                invoiceLines,
                taxes, vat);
    }

}
