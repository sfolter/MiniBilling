package com.github.methodia.minibilling;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class InvoiceLine {
    private int index;
    private BigDecimal quantity;
    private java.time.LocalDateTime start;
    private LocalDateTime end;
    private String product;
    private BigDecimal price;
    private int priceList;
    private BigDecimal amount;

    public InvoiceLine(int index, BigDecimal quantity, java.time.LocalDateTime start, java.time.LocalDateTime end, String product,
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

    public int getIndex() {
        return index;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public java.time.LocalDateTime getStart() {
        return start;
    }

    public java.time.LocalDateTime getEnd() {
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