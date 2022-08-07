package com.github.methodia.minibilling;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author Miroslav Kovachev
 * 28.07.2022
 * Methodia Inc.
 */
public class InvoiceLine {

    private final int index;
    private final BigDecimal quantity;
    private final LocalDateTime start;
    private final LocalDateTime end;
    private final String product;
    private final BigDecimal price;
    private final int priceList;
    private final BigDecimal amount;

    public InvoiceLine(int index, BigDecimal quantity, LocalDateTime start, LocalDateTime end, String product,
                       BigDecimal price, int priceList, BigDecimal amount) {
        this.index = index;
        this.quantity = quantity;
        this.start = start;
        this.end = end;
        this.product = product;
        this.price = price;
        this.priceList = priceList;
        this.amount = amount;
    }

    public BigDecimal getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "lines{" +
                "index:" + index +
                ", quantity:" + quantity +
                ", start:" + start +
                ", end:" + end +
                ", product:" + product + '\'' +
                ", price:" + price +
                ", priceList:" + priceList +
                ", amount:" + amount +
                '}';
    }
}