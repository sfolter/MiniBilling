package com.github.methodia.minibilling;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

public class InvoiceLineGenerator implements LineGenerator {

    public InvoiceLine generateInvoiceLine(int index, QuantityPricePeriod qpp, User user) {

        BigDecimal quantity = qpp.getQuantity().setScale(2, RoundingMode.HALF_UP).stripTrailingZeros();
        LocalDateTime lineStart = qpp.getStart();
        LocalDateTime lineEnd = qpp.getEnd();
        String product = qpp.getPrice().getProduct();
        BigDecimal price = qpp.getPrice().getValue();
        int priceList = user.getNumberPricingList();
        BigDecimal amount = qpp.getQuantity().multiply(qpp.getPrice().getValue()).setScale(2, RoundingMode.HALF_UP).stripTrailingZeros();

        return new InvoiceLine(index, quantity, lineStart, lineEnd, product, price, priceList, amount);

    }
}

