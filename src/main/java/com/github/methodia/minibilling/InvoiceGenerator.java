package com.github.methodia.minibilling;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.*;


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
    public Invoice generate(User user, Collection<Measurement> measurements, LocalDateTime dateReportingTo) {
        ProportionalMeasurementDistributor proportionalMeasurementDistributor =
                new ProportionalMeasurementDistributor(measurements, user.getPrice());
        Collection<QuantityPricePeriod> quantityPricePeriods = proportionalMeasurementDistributor.distribute();

        List<InvoiceLine> invoiceLines = new ArrayList<>();

        Set<String> productSet = new HashSet<>();

        for (QuantityPricePeriod qpp : quantityPricePeriods) {

            if (dateReportingTo.compareTo(qpp.getEnd()) >= 0) {
                int lineIndex = invoiceLines.size() + 1;
                InvoiceLine invoiceLine = createInvoiceLine(lineIndex, qpp, user);
                invoiceLines.add(invoiceLine);
                String product = qpp.getProduct();
                productSet.add(product);
            } else {
                break;
            }
        }
        String documentNumber = Invoice.getDocumentNumber();
        String userName = user.getName();
        List<Vat> vat = productSet.stream().map(a -> createVat(invoiceLines)).toList();
        BigDecimal totalAmount = invoiceLines.stream().map(InvoiceLine::getAmount).reduce(new BigDecimal(0)
                , BigDecimal::add);
        BigDecimal totalAmountWithVat = vat.stream().map(Vat::getAmount).reduce(totalAmount, BigDecimal::add);
        return new Invoice(documentNumber, userName, user.getRef(), totalAmount, totalAmountWithVat, invoiceLines, vat);
    }

    private Vat createVat(List<InvoiceLine> invoiceLines) {
        List<Integer> vattedLines = invoiceLines.stream().map(InvoiceLine::getIndex).toList();
        int counter = 0;
        counter += 1;
        BigDecimal amount = invoiceLines.stream().map(InvoiceLine::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add).multiply(new BigDecimal("0.2"))
                .setScale(2, RoundingMode.HALF_UP).stripTrailingZeros();
        return new Vat(counter, vattedLines, amount);
    }

    private InvoiceLine createInvoiceLine(int lineIndex, QuantityPricePeriod qpp, User user) {
        BigDecimal quantity = qpp.getQuantity();
        LocalDateTime start = qpp.getStart();
        LocalDateTime end = qpp.getEnd();
        BigDecimal price = qpp.getPrice();
        BigDecimal amount = qpp.getQuantity().multiply(qpp.getPrice())
                .multiply(currencyConverter.getCurrencyValue(user.getCyrrency()))
                .setScale(2, RoundingMode.HALF_UP).stripTrailingZeros();
        //TODO add product to qpp; remove the following code
        String product = qpp.getProduct();
        return new InvoiceLine(lineIndex, quantity, start, end,
                product, price, user.getNumberPricingList(), amount);
    }


}