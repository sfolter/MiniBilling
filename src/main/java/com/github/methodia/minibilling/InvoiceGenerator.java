package com.github.methodia.minibilling;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class InvoiceGenerator {

    final private CurrencyConverter currencyConverter;

    public InvoiceGenerator(CurrencyConverter currencyConverter) {
        this.currencyConverter = currencyConverter;
    }

    /**
     * Looping through MMs and Prices and comparing the end date of measurements dates with pricing dates and if
     * its needed, dividing them into different lines,also geting the data(,quantity,price,start of the line,
     * end of the line etc.
     */
    public Invoice generate(User user, Collection<Measurement> measurements, LocalDateTime dateReportingTo,
                            List<VatPercentages> vatPercentages) {
        ProportionalMeasurementDistributor proportionalMeasurementDistributor =
                new ProportionalMeasurementDistributor(measurements, user.getPrice());
        Collection<QuantityPricePeriod> quantityPricePeriods = proportionalMeasurementDistributor.distribute();

        List<InvoiceLine> invoiceLines = new ArrayList<>();



        List<Tax> taxList = new ArrayList<>();
        TaxGenerator taxGenerator = new TaxGenerator();
        for (QuantityPricePeriod qpp : quantityPricePeriods) {
            if (dateReportingTo.compareTo(qpp.getEnd()) >= 0) {
                int lineIndex = invoiceLines.size() + 1;
                InvoiceLine invoiceLine = createInvoiceLine(lineIndex, qpp, user);
                invoiceLines.add(invoiceLine);
                taxList.add(taxGenerator.generate(new BigDecimal("1.6"), invoiceLine, taxList.size()));
            } else {
                break;
            }
        }

        String documentNumber = Invoice.getDocumentNumber();
        String userName = user.getName();
        List<Vat> vat = new VatGenerator().generate(vatPercentages, invoiceLines, taxList);

        BigDecimal taxAmount = taxList.stream().map(Tax::getAmount).reduce(new BigDecimal(0), BigDecimal::add);
        BigDecimal totalAmount = invoiceLines.stream().map(InvoiceLine::getAmount).reduce(taxAmount, BigDecimal::add);
        BigDecimal totalAmountWithVat = vat.stream().map(Vat::getAmount).reduce(totalAmount, BigDecimal::add);

        return new Invoice(documentNumber, userName, user.getRef(), totalAmount, totalAmountWithVat, invoiceLines,
                vat, taxList);
    }

    private InvoiceLine createInvoiceLine(int lineIndex, QuantityPricePeriod qpp, User user) {
        BigDecimal quantity = qpp.getQuantity();
        LocalDateTime start = qpp.getStart();
        LocalDateTime end = qpp.getEnd();
        BigDecimal price = qpp.getPrice();
        BigDecimal amount = qpp.getQuantity().multiply(qpp.getPrice())
                .setScale(2, RoundingMode.HALF_UP).stripTrailingZeros();
        // add the following method in order to set up your currency converter
        // .multiply(currencyConverter.getCurrencyValue(user.getCyrrency()))
        //TODO add product to qpp; remove the following code
        String product = qpp.getProduct();
        return new InvoiceLine(lineIndex, quantity, start, end,
                product, price, user.getNumberPricingList(), amount);
    }


}