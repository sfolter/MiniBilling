package com.example.SpringBatchExample.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "invoice_line")

public class InvoiceLine {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name = "index")
    private int index;
    @Column(name = "quantity")
    private BigDecimal quantity;
    @Column(name = "line_start")
    private LocalDateTime lineStart;
    @Column(name = "line_end")
    private LocalDateTime lineEnd;
    @Column(name = "product")

    private String product;
    @Column(name = "price")

    private BigDecimal price;
    @Column(name = "price_list")
    private int priceList;
    @Column(name = "amount")

    private BigDecimal amount;

    public InvoiceLine() {

    }

    public InvoiceLine(final int index, final BigDecimal quantity, final LocalDateTime start, final LocalDateTime end,
                       final String product,
                       final BigDecimal price, final int priceList, final BigDecimal amount) {
        this.index = index;
        this.quantity = quantity;
        lineStart = start;
        lineEnd = end;
        this.product = product;
        this.price = price;
        this.priceList = priceList;
        this.amount = amount;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public BigDecimal getAmount() {
        return amount;
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

    @Override
    public String toString() {
        return "InvoiceLine{" +
                "id=" + id +
                ", index=" + index +
                ", quantity=" + quantity +
                ", lineStart=" + lineStart +
                ", lineEnd=" + lineEnd +
                ", product='" + product + '\'' +
                ", price=" + price +
                ", priceList=" + priceList +
                ", amount=" + amount +
                '}';
    }
}