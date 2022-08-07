package com.github.methodia.minibilling;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class InvoiceLine {
    private int index;
    private BigDecimal quantity;
    private String lineStart;
    private String lineEnd;
    private String product;
    private BigDecimal price;
    private int priceList;
    private BigDecimal amount;

    public InvoiceLine(int index, BigDecimal quantity, String lineStart, String lineEnd, String product,
                       BigDecimal price, int priceList, BigDecimal amount) {
        this.index = index;
        this.quantity = quantity;
        this.lineStart = lineStart;
        this.lineEnd = lineEnd;
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

    public String getLineStart() {
        return lineStart;
    }

    public String getLineEnd() {
        return lineEnd;
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
