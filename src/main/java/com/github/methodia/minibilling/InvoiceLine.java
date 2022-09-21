package com.github.methodia.minibilling;

import java.math.BigDecimal;
import java.time.LocalDateTime;


public class InvoiceLine {

    private final int index;
    private final BigDecimal quantity;
    private final LocalDateTime start;
    private final LocalDateTime end;
    private final String product;
    private final BigDecimal price;
    private final int priceList;
    private final BigDecimal amount;

    public InvoiceLine(final int index, final BigDecimal quantity, final LocalDateTime start, final LocalDateTime end,
                       final String product,
                       final BigDecimal price, final int priceList, final BigDecimal amount) {
        this.index = index;
        this.quantity = quantity;
        this.start = start;
        this.end = end;
        this.product = product;
        this.price = price;
        this.priceList = priceList;
        this.amount = amount;
    }

    public int getIndex() {
        return index;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public String getProduct() {
        return product;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public int getPriceList() {
        return priceList;
    }

    public BigDecimal getAmount() {
        return amount;
    }
}
