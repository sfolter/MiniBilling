package com.github.methodia.minibilling.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "invoice_lines")
public class InvoiceLine {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "index")
    private int index;
    @Column(name = "quantity")
    private BigDecimal quantity;
    @Column(name = "linesStart")
    private LocalDateTime lineStart;
    @Column(name = "linesEnd")
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

    public InvoiceLine(final int index, final BigDecimal quantity, final LocalDateTime lineStart,
                       final LocalDateTime lineEnd, final String product,
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



    public void setIndex(final int index) {
        this.index = index;
    }

    public void setQuantity(final BigDecimal quantity) {
        this.quantity = quantity;
    }

    public void setProduct(final String product) {
        this.product = product;
    }

    public void setPrice(final BigDecimal price) {
        this.price = price;
    }

    public void setPriceList(final int priceList) {
        this.priceList = priceList;
    }

    public void setAmount(final BigDecimal amount) {
        this.amount = amount;
    }
}
