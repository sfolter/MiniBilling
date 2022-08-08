package com.github.methodia.minibilling;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


public class InvoiceGenerator {
    private User user;
    private Collection<Measurement> measurements;
    private Collection<Price> prices;

    public InvoiceGenerator(User user, Collection<Measurement> measurements, Collection<Price> prices) {
        this.user = user;
        this.measurements = measurements;
        this.prices = prices;
    }

    public Invoice generate(LocalDateTime dateReportingTo) {
        /**Looping through MMs and Prices and comparing the end date of measurements dates with pricing dates and if
         * its needed, dividing them into different lines,also geting the data(,quantity,price,start of the line,
         * end of the line etc. */
        ProportionalMeasurementDistributor proportionalMeasurementDistributor=
                new ProportionalMeasurementDistributor(getMeasurements(),getPrices());
        Collection<QuantityPricePeriod> quantityPricePeriods=proportionalMeasurementDistributor.distribute();

        List<InvoiceLine> invoiceLines=new ArrayList<>();

        BigDecimal amount=BigDecimal.ZERO;
        BigDecimal totalAmount=BigDecimal.ZERO;
        int lineIndex=0;
       /**Looping through QuantityPricePeriods and filling Invoice fields*/
        for (QuantityPricePeriod qpp : quantityPricePeriods) {
            if (dateReportingTo.compareTo(qpp.getEnd()) >= 0) {
                BigDecimal quantity = qpp.getQuantity();
                LocalDateTime start = qpp.getStart();
                LocalDateTime end = qpp.getEnd();
                String product = null;
                BigDecimal price = qpp.getPrice();
                amount = qpp.getQuantity().multiply(qpp.getPrice())
                        .setScale(2,RoundingMode.HALF_UP).stripTrailingZeros();
                totalAmount = totalAmount.add(new BigDecimal(String.valueOf(amount)))
                        .setScale(2,RoundingMode.HALF_UP).stripTrailingZeros();

                /**Looping through prices to get the product of price as User carry a List of Prices,
                so we have to iterate through them and get the product for the current indexLine*/
               List<Price>  productList =getUser().getPrice().stream()
                        .filter(price1 ->qpp.getEnd().toLocalDate().compareTo(price1.getEnd())<=0
                                &&qpp.getPrice().equals(price1.getValue())).toList();

                    product=String.valueOf(productList.get(0).getProduct());

                lineIndex = invoiceLines.size() + 1;
                invoiceLines.add(new InvoiceLine(lineIndex, quantity, start, end,
                        product, price, getUser().getNumberPricingList(), amount));
            }else{
                break;
            }
        }
            String documentNumber = Invoice.getDocumentNumber();
            String userName = getUser().getName();

       return new Invoice(  documentNumber,userName,getUser().getRef(),totalAmount,invoiceLines);
    }

    public User getUser() {
        return user;
    }

    public Collection<Measurement> getMeasurements() {
        return measurements;
    }

    public Collection<Price> getPrices() {
        return prices;
    }
}
