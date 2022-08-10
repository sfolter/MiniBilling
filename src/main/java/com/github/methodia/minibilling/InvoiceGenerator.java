package com.github.methodia.minibilling;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
        ProportionalMeasurementDistributor proportionalMeasurementDistributor = new ProportionalMeasurementDistributor(measurements, prices);
        List<QuantityPricePeriod> distribute = proportionalMeasurementDistributor.distribute();
        YearMonth yearMonth = YearMonth.parse(yearMonthStr, DateTimeFormatter.ofPattern("yy-MM"));
        final LocalDateTime yearMonthLocalDate = yearMonth.atEndOfMonth().atTime(23, 59, 59);
        List<InvoiceLine> invoiceLines = new ArrayList<>();
        List<VAT> vatLines =new ArrayList<>();
        List<Integer> vatList = new ArrayList<>();
        BigDecimal totalAmount = BigDecimal.ZERO;
        int counter = 1;
        for (QuantityPricePeriod qpp : distribute) {
            LocalDateTime end = qpp.getEnd();
            if (yearMonthLocalDate.compareTo(end) >= 0) {
            int index = counter;
            BigDecimal quantity = qpp.getQuantity();
            LocalDateTime start = qpp.getStart();
            String product = qpp.getPrice().getProduct();
            BigDecimal price = qpp.getPrice().getValue();
            int priceList = user.getPriceListNumber();
            BigDecimal amount = qpp.getQuantity().multiply(qpp.getPrice().getValue());
            totalAmount = totalAmount.add(amount);
            int indexInVat = counter;
            vatList.add(invoiceLines.size());
            int percentage = 20;
            BigDecimal amountInVat = amount.multiply(new BigDecimal(20).divide(new BigDecimal(100)));
            BigDecimal totalAmountWithWat;

                invoiceLines.add(new InvoiceLine(index, quantity, start, end, product, price, priceList, amount));
                vatLines.add(new VAT(indexInVat, vatList, percentage, amountInVat));
                counter++;
            }
        }
        LocalDateTime documentDate = LocalDateTime.now();
        String documentNumber = Invoice.getDocumentNumber();
        User consumer = user;

        return new Invoice(documentDate, documentNumber, consumer, totalAmount, invoiceLines,vatLines);
    }
}