package com.github.methodia.minibilling;

import com.github.methodia.minibilling.entityClasses.Invoice;
import com.github.methodia.minibilling.entityClasses.InvoiceLine;
import com.github.methodia.minibilling.entityClasses.Tax;
import com.github.methodia.minibilling.entityClasses.User;
import com.github.methodia.minibilling.entityClasses.Vat;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class InvoiceGenerator {

    private final CurrencyConverter currencyConverter;

    public InvoiceGenerator(final CurrencyConverter currencyConverter) {
        this.currencyConverter = currencyConverter;
    }

    /**
     * Looping through MMs and Prices and comparing the end date of measurements dates with pricing dates and if
     * its needed, dividing them into different lines,also getting the data(,quantity,price,start of the line,
     * end of the line etc.
     */
    public Invoice generate(final User user, final Collection<Measurement> measurements, final LocalDateTime dateReportingTo,
                            final List<VatPercentages> vatPercentages) {
        final ProportionalMeasurementDistributor proportionalMmDistributor =
                new ProportionalMeasurementDistributor(measurements, user.getPricesList().prices);
        final Collection<QuantityPricePeriod> quantityPricePeriods = proportionalMmDistributor.distribute();

        final List<InvoiceLine> invoiceLines = new ArrayList<>();
        final List<Tax> taxList = new ArrayList<>();
        final TaxGenerator taxGenerator = new TaxGenerator();
        final BigDecimal currencyValue = currencyConverter.getCurrencyValue(user.getCurrency());
        for (final QuantityPricePeriod qpp : quantityPricePeriods) {
            if (0 <= dateReportingTo.compareTo(qpp.end())) {
                final int lineIndex = invoiceLines.size() + 1;
                final InvoiceLine invoiceLine = createInvoiceLine(lineIndex, qpp, user,currencyValue);
                invoiceLines.add(invoiceLine);
                taxList.add(taxGenerator.generate( invoiceLine,new BigDecimal("1.6"),
                        currencyValue, taxList.size()));
            } else {
                break;
            }
        }

        final Integer documentNumber = Integer.valueOf(Invoice.getDocumentNumber());
        final String userName = user.getName();
        final List<Vat> vat = new VatGenerator().generate(vatPercentages, invoiceLines, taxList);

        final BigDecimal taxAmount = taxList.stream().map(Tax::getAmount).reduce(new BigDecimal(0), BigDecimal::add);
        final BigDecimal totalAmount = invoiceLines.stream().map(InvoiceLine::getAmount).reduce(taxAmount, BigDecimal::add);
        final BigDecimal totalAmountWithVat = vat.stream().map(Vat::getAmount).reduce(totalAmount, BigDecimal::add);

        return new Invoice(documentNumber, userName, user.getRefNumber(), totalAmount, totalAmountWithVat, invoiceLines,
                vat, taxList);
    }

    private InvoiceLine createInvoiceLine(final int lineIndex, final QuantityPricePeriod qpp, final User user, final BigDecimal currencyValue) {
        final BigDecimal quantity = qpp.quantity();
        final LocalDateTime start = qpp.start();
        final LocalDateTime end = qpp.end();
        final BigDecimal price = qpp.price();

        final BigDecimal amount = qpp.quantity().multiply(qpp.price())
                .multiply(currencyValue)
                .setScale(2, RoundingMode.HALF_UP).stripTrailingZeros();
        // add the following method in order to set up your currency converter

        final String product = qpp.product();
        return new InvoiceLine(lineIndex, quantity, start, end,
                product, price, user.getPriceList().getId(), amount);
    }


}