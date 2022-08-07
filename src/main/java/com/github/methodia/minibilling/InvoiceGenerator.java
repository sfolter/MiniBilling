package com.github.methodia.minibilling;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
    private final User user;
    private final Collection<Measurement> measurements;
    private final Collection<Price> prices;

    public InvoiceGenerator(User user, Collection<Measurement> measurements, Collection<Price> prices) {
        this.user = user;
        this.measurements = measurements;
        this.prices = prices;
    }

    public Invoice generate(LocalDateTime dateReportingTo) {

        ProportionalMeasurementDistributor proportionalMeasurementDistributor = new ProportionalMeasurementDistributor(measurements, prices);
        Collection<QuantityPricePeriod> quantityPricePeriods = proportionalMeasurementDistributor.distribute();

        List<InvoiceLine> invoiceLines = new ArrayList<>();

        BigDecimal variable = new BigDecimal(0);
        BigDecimal amount = BigDecimal.ZERO;
        BigDecimal totalAmount = null;
        int lineIndex=0;
        for (QuantityPricePeriod qpp : quantityPricePeriods) {
            if (dateReportingTo.compareTo(qpp.getEnd()) >= 0) {

            BigDecimal quantity = qpp.getQuantity();
            LocalDateTime lineStart = qpp.getStart();
            LocalDateTime lineEnd = qpp.getEnd();
            String product = qpp.getPrice().getProduct();
            BigDecimal price = qpp.getPrice().getValue();
            int priceList = user.getNumberPricingList();

            amount = qpp.getQuantity().multiply(qpp.getPrice().getValue());
            totalAmount = variable.add(new BigDecimal(String.valueOf(amount)));
                int index = invoiceLines.size() + 1;
            invoiceLines.add(new InvoiceLine(index, quantity, lineStart, lineEnd, product, price, priceList, amount));

            }else{
                continue;
            }
        }

        String documentNumber = Invoice.getDocumentNumber();

        String consumer = user.getName();

        return new Invoice( documentNumber, consumer,user.getRef(), totalAmount, invoiceLines);

    }


}