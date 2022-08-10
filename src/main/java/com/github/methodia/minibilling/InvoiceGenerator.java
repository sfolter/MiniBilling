package com.github.methodia.minibilling;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Miroslav Kovachev
 * 28.07.2022
 * Methodia Inc.
 */
public class InvoiceGenerator {
    private User user;
    private Collection<Measurement> measurements;
    private Collection<Price> prices;
    private String yearMonthStr;


    public InvoiceGenerator(User user, Collection<Measurement> measurements, Collection<Price> prices, String yearMonthStr) {
        this.user = user;
        this.measurements = measurements;
        this.prices = prices;
        this.yearMonthStr = yearMonthStr;
    }

    public Invoice generate() {
        ProportionalMeasurementDistributor proportionalMeasurementDistributor = new ProportionalMeasurementDistributor(measurements,prices);
        List<QuantityPricePeriod> distribute = proportionalMeasurementDistributor.distribute();
        YearMonth yearMonth = YearMonth.parse(yearMonthStr, DateTimeFormatter.ofPattern("yy-MM"));
        final LocalDateTime yearMonthLocalDate=yearMonth.atEndOfMonth().atTime(23,59,59);
        List<InvoiceLine> invoiceLines = new ArrayList<>();
        BigDecimal totalAmount = new BigDecimal(0);
        int counter = 1;
        //BigDecimal amount = BigDecimal.valueOf(0)

        for (QuantityPricePeriod qpp : distribute) {
            int index = counter;
            BigDecimal quantity = qpp.getQuantity();
            LocalDateTime start = qpp.getStart();
            LocalDateTime end = qpp.getEnd();
            String product = qpp.getPrice().getProduct();
            BigDecimal price = qpp.getPrice().getValue();
            int priceList = user.getPriceListNumber();
            BigDecimal amount = qpp.getQuantity().multiply(qpp.getPrice().getValue());
            totalAmount = totalAmount.add(amount);
            if (yearMonthLocalDate.compareTo(end) >= 0) {
                invoiceLines.add(new InvoiceLine(index, quantity, start, end, product, price, priceList, amount));
                counter++;
            }
        }

        LocalDateTime documentDate = LocalDateTime.now();
        String documentNumber = Invoice.getDocumentNumber();
        User consumer = user;

        return new Invoice(documentDate, documentNumber, consumer, totalAmount, invoiceLines);
    }


}
