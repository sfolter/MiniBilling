package com.github.methodia.minibilling;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class InvoiceGenerator {
    private final User user;
    private final Collection<Measurement> measurements;
    private final Collection<Price> prices;

    public InvoiceGenerator(User user, Collection<Measurement> measurements, Collection<Price> prices) {
        this.user = user;
        this.measurements = measurements;
        this.prices = prices;
    }

    public Invoice generate(long documentNumber,String borderTime) {
        ProportionalMeasurementDistributor proportionalMeasurementDistributor = new ProportionalMeasurementDistributor(measurements, prices);
        Collection<QuantityPricePeriod> quantityPricePeriods = proportionalMeasurementDistributor.distribute();

        List<InvoiceLine> invoiceLines = new ArrayList<>();
        int counter = 1;
        BigDecimal totalBigDecimal = BigDecimal.ZERO;
        for (QuantityPricePeriod qpp : quantityPricePeriods) {
            int index = counter++;
            BigDecimal quantity = qpp.getQuantity();
            LocalDateTime start = qpp.getStart().toLocalDateTime();
            LocalDateTime end = qpp.getEnd().toLocalDateTime();
            String product = qpp.getPrice().getProduct();
            BigDecimal price = qpp.getPrice().getValue();
            int priceList = user.getPriceListNumber();
            BigDecimal amount = qpp.getQuantity().multiply(qpp.getPrice().getValue());
            totalBigDecimal = totalBigDecimal.add(amount);
            LocalDate borderDate = Formatter.parseBorder(borderTime);
            if (qpp.getEnd().toLocalDate().isBefore(borderDate)) {
                invoiceLines.add(new InvoiceLine(index, quantity, start, end, product, price, priceList, amount));
            }
        }
        LocalDateTime documentDate = LocalDateTime.now();
        String docNumber=String.valueOf(documentNumber);
        String consumer = user.getName();
        String reference = user.getRef();
        return new Invoice(documentDate, docNumber, consumer, reference, totalBigDecimal, invoiceLines);
    }

}
