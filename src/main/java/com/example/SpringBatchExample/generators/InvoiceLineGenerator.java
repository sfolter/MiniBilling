package com.example.SpringBatchExample.generators;

import com.example.SpringBatchExample.QuantityPricePeriod;
import com.example.SpringBatchExample.models.InvoiceLine;
import com.example.SpringBatchExample.models.User;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;


public class InvoiceLineGenerator implements LineGenerator {

    @Override
    public InvoiceLine generateInvoiceLine(final int index, final QuantityPricePeriod qpp, final User user) {

        final BigDecimal quantity = qpp.getQuantity().setScale(2, RoundingMode.HALF_UP).stripTrailingZeros();
        final LocalDateTime lineStart = LocalDateTime.from(qpp.getStart());
        final LocalDateTime lineEnd = LocalDateTime.from(qpp.getEnd().minusSeconds(1));
        final String product = qpp.getPrice().getProduct();
        final BigDecimal price = qpp.getPrice().getValue();
        final int priceList = user.getNumberPricingList();

             /*  CurrencyConverter currencyConverter = new CurrencyConverter();
                BigDecimal currencyRate = currencyConverter.convertTo(currencyFrom,currencyTo,amount);*/

        final BigDecimal currencyRate = new BigDecimal("0.5117");
        final BigDecimal amount = qpp.getQuantity().multiply(qpp.getPrice().getValue())
                .multiply(currencyRate)
                .setScale(2, RoundingMode.HALF_UP).stripTrailingZeros();

        return new InvoiceLine(index, quantity, lineStart, lineEnd, product, price, priceList, amount);

    }
}

