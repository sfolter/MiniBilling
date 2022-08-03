package com.github.methodia.minibilling;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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

    public InvoiceGenerator(User user, Collection<Measurement> measurements, Collection<Price> prices) {
        this.user = user;
        this.measurements = measurements;
        this.prices = prices;
    }

    public Invoice generate() {
        ProportionalMeasurementDistributor proportionalMeasurementDistributor = new ProportionalMeasurementDistributor(measurements,prices);
        List<QuantityPricePeriod> distribute = proportionalMeasurementDistributor.distribute();
        for (int i = 0; i < distribute.size(); i++) {
            BigDecimal quantity = distribute.get(i).getQuantity();
            LocalDateTime end = distribute.get(i).getEnd();
            LocalDateTime start = distribute.get(i).getStart();
            BigDecimal price = distribute.get(i).getPrice();
            String product = Price.getProduct();

//            List<Price> priceList = User;
//            int priceListNumber = Integer.parseInt((priceList.get(0)).toString());
            BigDecimal amount = quantity.multiply(price);
            int index = 1;
//            InvoiceLine invoiceLine = new InvoiceLine(index,quantity,start, end, product, price, priceListNumber, amount);
        }
//        InvoiceLine invoiceLine = new InvoiceLine()
        //TODO
        throw new UnsupportedOperationException("Not implemented!");
    }


}
