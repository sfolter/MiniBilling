package com.github.methodia.minibilling;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class InvoiceGenerator {
    private User user;
    private Collection<Measurement> measurements;
    private Collection<Price> prices;

    public InvoiceGenerator(User user, Collection<Measurement> measurements, Collection<Price> prices) {
        this.user = user;
        this.measurements = measurements;
        this.prices = prices;
    }

    public Invoice generate() {
        ProportionalMeasurementDistributor proportionalMeasurementDistributor = new ProportionalMeasurementDistributor(measurements, prices);
        Collection<QuantityPricePeriod> quantityPricePeriods = proportionalMeasurementDistributor.distribute();

        List<InvoiceLine> invoiceLines = new ArrayList<>();
        int counter = 1;
        double total = 0;
        BigDecimal totalBigDecimal=BigDecimal.ZERO;
        for (QuantityPricePeriod qpp : quantityPricePeriods) {
            int index = counter++;
            BigDecimal quantity = qpp.getQuantity();
            LocalDateTime start = qpp.getStart();
            LocalDateTime end = qpp.getEnd();
            String product = qpp.getPrice().getProduct();
            BigDecimal price = qpp.getPrice().getValue();
            int priceList = user.getPriceListNumber();
            BigDecimal amount = qpp.getQuantity().multiply(qpp.getPrice().getValue());
            totalBigDecimal=totalBigDecimal.add(amount);
            invoiceLines.add(new InvoiceLine(index, quantity, start, end, product, price, priceList, amount));
        }
        LocalDateTime documentDate = LocalDateTime.now();
        String documentNumber = Invoice.getDocumentNumber();
        String consumer = user.getName();
        String reference = user.getRef();
        return new Invoice(documentDate, documentNumber, consumer, reference, totalBigDecimal, invoiceLines);
    }

}
