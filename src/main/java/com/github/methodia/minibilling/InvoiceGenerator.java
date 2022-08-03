package com.github.methodia.minibilling;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;

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
        ProportionalMeasurementDistributor proportionalMeasurementDistributor=new ProportionalMeasurementDistributor(measurements,prices);
        Collection<QuantityPricePeriod> quantityPricePeriods=proportionalMeasurementDistributor.distribute();
        LocalDateTime documentDate=LocalDateTime.now();
        String documentNumber=Invoice.getDocumentNumber();
        User consumer=user; //можеби стринг с името
        BigDecimal totalAmount;// сума амаунт

        int counter=0;
        for(QuantityPricePeriod qpp:quantityPricePeriods){
            int index=counter++;
            BigDecimal quantity=qpp.getQuantity();
            LocalDateTime start=qpp.getStart();
            LocalDateTime end=qpp.getEnd();
            String product;//???
            BigDecimal price=qpp.getPrice();
            int priceList;
            BigDecimal amount=qpp.getQuantity().multiply(qpp.getPrice());

        }
        //TODO

        throw new UnsupportedOperationException("Not implemented!");
    }
}
