package com.github.methodia.minibilling;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author Miroslav Kovachev
 * 28.07.2022
 * Methodia Inc.
 */
public class InvoiceLine {

    private int index;
    private BigDecimal quantity;
    private LocalDateTime lineStart;
    private LocalDateTime lineEnd;
    private String product;
    private BigDecimal price;
    private int priceList;
    private BigDecimal amount;

    public InvoiceLine(int index, BigDecimal quantity, LocalDateTime start, LocalDateTime end, String product,
                       BigDecimal price, int priceList, BigDecimal amount) {
        this.index = index;
        this.quantity = quantity;
        this.lineStart = start;
        this.lineEnd = end;
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
        return lineStart;
    }

    public LocalDateTime getEnd() {
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

    @Override
    public String toString() {
        return "InvoiceLine{" +
                "index=" + index +
                ", quantity=" + quantity +
                ", start=" + lineStart +
                ", end=" + lineEnd +
                ", product='" + product + '\'' +
                ", price=" + price +
                ", priceList=" + priceList +
                ", amount=" + amount +
                '}';
    }
}
