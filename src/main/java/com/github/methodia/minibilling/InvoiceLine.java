package com.github.methodia.minibilling;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class InvoiceLine {

    private final int index;
    private final BigDecimal quantity;
    private final LocalDateTime lineStart;
    private final LocalDateTime lineEnd;
    private final String product;
    private final BigDecimal price;
    private final int priceList;
    private final BigDecimal amount;

    public InvoiceLine(final int index, final BigDecimal quantity, final LocalDateTime lineStart, final LocalDateTime lineEnd, final String product,
                       final BigDecimal price, final int priceList, final BigDecimal amount) {
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

    public LocalDateTime getLineStart() {
        return lineStart;
    }

    public LocalDateTime getLineEnd() {
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
