package com.github.methodia.minibilling;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
        ProportionalMeasurementDistributor proportionalMeasurementDistributor=new ProportionalMeasurementDistributor(measurements,prices);
        Collection<QuantityPricePeriod> quantityPricePeriods=proportionalMeasurementDistributor.distribute();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
           LocalDateTime documentDate= LocalDateTime.parse(LocalDateTime.now().format(formatter));
        List<InvoiceLine> invoiceLines=new ArrayList<>();
        BigDecimal variable=new BigDecimal(0);
        BigDecimal amount=BigDecimal.ZERO;
        BigDecimal totalAmount=null;
        int counter = 0;
        for (QuantityPricePeriod qpp : quantityPricePeriods) {
            int index = counter++;
            BigDecimal quantity = qpp.getQuantity();
            LocalDateTime start = qpp.getStart();
            LocalDateTime end = qpp.getEnd();
            String product = null;//???
            BigDecimal price = qpp.getPrice();
             amount = qpp.getQuantity().multiply(qpp.getPrice());
            totalAmount = variable.add(new BigDecimal(String.valueOf(amount)));


            for (Price priceFromLoop : user.getPrice()) {
                if (qpp.getStart().toLocalDate().compareTo(priceFromLoop.getStart()) >= 0 &&
                        qpp.getEnd().toLocalDate().compareTo(priceFromLoop.getEnd()) <= 0 && qpp.getPrice().equals(priceFromLoop.getValue())) {
                    product = priceFromLoop.getProduct();
                }
            }
            invoiceLines.add(new InvoiceLine(counter,quantity,start,end,product,price,user.getNumberPricingList(),amount));
        counter+=1;
        }
        String documentNumber=Invoice.getDocumentNumber();
        String userName=user.getName();

       return new Invoice(  documentDate,documentNumber,userName,totalAmount,invoiceLines);
    }
}
